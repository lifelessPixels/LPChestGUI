package pl.lifelesspixels.lpchestgui.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.lifelesspixels.lpchestgui.LPChestGUI;
import pl.lifelesspixels.lpchestgui.data.ClickType;
import pl.lifelesspixels.lpchestgui.data.GUIManager;

import java.util.Objects;

public abstract class BaseGUI {

    protected static final ItemStack EMPTY_INVENTORY_SLOT_ITEM;

    static {
        // create empty slot item stack
        ItemStack emptySlotItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta emptySlotMeta = Objects.requireNonNull(emptySlotItem.getItemMeta());
        emptySlotMeta.setDisplayName(ChatColor.GRAY + ""); // NOTE: "chat color" produces smaller box on hover than space
        emptySlotItem.setItemMeta(emptySlotMeta);
        EMPTY_INVENTORY_SLOT_ITEM = emptySlotItem;
    }

    public final void openFor(Player player) {
        GUIManager guiManager = LPChestGUI.getInstance().getGUIManager();
        if(guiManager.hasOpenGUI(player)) {
            scheduleInventoryReopenFor(player);
        } else {
            guiManager.setGUIForPlayer(player, this);
            onOpen(player);
            player.openInventory(getBackingInventory());
        }
    }

    public final void closeGUIFor(Player player) {
        scheduleInventoryCloseFor(player);
    }

    public void onOpen(Player player) { }
    public void onGUIItemClicked(Player player, int slot, ClickType clickType) { }
    public void onPlayerItemClicked(Player player, int slot, ClickType clickType) { }
    public void onInventoryClosed(Player player) { }

    public boolean isBackingInventory(Inventory inventory) {
        return getBackingInventory() == inventory;
    }

    protected abstract Inventory getBackingInventory();

    protected void fillInventoryWithEmptySlotItems(Inventory inventory) {
        for(int index = 0; index < inventory.getSize(); index++)
            inventory.setItem(index, EMPTY_INVENTORY_SLOT_ITEM);
    }

    protected void fillInventoryWithEmptySlotItems(Inventory inventory, int startIndex, int endIndex) {
        for(int index = startIndex; index <= endIndex; index++)
            inventory.setItem(index, EMPTY_INVENTORY_SLOT_ITEM);
    }

    private void scheduleInventoryReopenFor(Player player) {
        Bukkit.getScheduler().runTask(LPChestGUI.getInstance(), () -> {
            closeInventoryFor(player);
            Bukkit.getScheduler().runTask(LPChestGUI.getInstance(), () -> {
                openFor(player);
            });
        });
    }

    private static void scheduleInventoryCloseFor(Player player) {
        // NOTE: closing of the inventory must be scheduler for the next tick
        Bukkit.getScheduler().runTask(LPChestGUI.getInstance(), () -> closeInventoryFor(player));
    }

    private static void closeInventoryFor(Player player) {
        GUIManager guiManager = LPChestGUI.getInstance().getGUIManager();
        guiManager.removeGUIForPlayer(player);
        player.closeInventory();
    }

}
