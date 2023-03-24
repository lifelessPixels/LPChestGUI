package pl.lifelesspixels.lpchestgui.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.lifelesspixels.lpchestgui.LPChestGUI;
import pl.lifelesspixels.lpchestgui.data.*;

import java.util.Objects;

public class ChestGUI {

    private static final ItemStack EMPTY_INVENTORY_SLOT_ITEM;

    static {
        // create empty slot item stack
        ItemStack emptySlotItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta emptySlotMeta = Objects.requireNonNull(emptySlotItem.getItemMeta());
        emptySlotMeta.setDisplayName(ChatColor.GRAY + ""); // NOTE: "chat color" produces smaller box on hover than space
        emptySlotItem.setItemMeta(emptySlotMeta);
        EMPTY_INVENTORY_SLOT_ITEM = emptySlotItem;
    }

    private final int rows;
    private final GUIAction[] actions;
    private final Sound[] actionSounds;
    private final Inventory backingInventory;

    public ChestGUI(int rows) {
        if(rows < 1 || rows > 6)
            throw new IllegalArgumentException("inventory rows must be at least 0 and at most 6");

        this.rows = rows;
        this.actions = new GUIAction[rows * 9];
        this.actionSounds = new Sound[rows * 9];
        this.backingInventory = Bukkit.createInventory(null, rows * 9);
        fillInventoryWithEmptySlotItems();
    }

    public ChestGUI(int rows, String title) {
        if(rows < 1 || rows > 6)
            throw new IllegalArgumentException("inventory rows must be at least 0 and at most 6");

        this.rows = rows;
        this.actions = new GUIAction[rows * 9];
        this.actionSounds = new Sound[rows * 9];
        this.backingInventory = Bukkit.createInventory(null, rows * 9, title);
        fillInventoryWithEmptySlotItems();
    }

    public void setAction(int slot, GUIAction action, ItemStack item) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");
        if(!Objects.requireNonNull(backingInventory.getItem(slot)).isSimilar(EMPTY_INVENTORY_SLOT_ITEM))
            throw new IllegalArgumentException("cannot set action for non-empty slot");

        actions[slot] = action;
        backingInventory.setItem(slot, item);
    }

    public void setAction(int slot, GUIAction action, ItemStack item, Sound actionSound) {
        setAction(slot, action, item);
        actionSounds[slot] = actionSound;
    }

    public void setDummyItem(int slot, ItemStack item) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");
        if(!Objects.requireNonNull(backingInventory.getItem(slot)).isSimilar(EMPTY_INVENTORY_SLOT_ITEM))
            throw new IllegalArgumentException("cannot set dummy item for non-empty slot");

        backingInventory.setItem(slot, item);
    }

    public void resetSlot(int slot) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");

        actions[slot] = null;
        actionSounds[slot] = null;
        backingInventory.setItem(slot, EMPTY_INVENTORY_SLOT_ITEM);
    }

    public void openFor(Player player) {
        GUIManager guiManager = LPChestGUI.getInstance().getGUIManager();
        if(guiManager.hasOpenGUI(player)) {
            scheduleInventoryReopenFor(player);
        } else {
            guiManager.setGUIForPlayer(player, this);
            player.openInventory(backingInventory);
        }
    }

    public void closeFor(Player player) {
        scheduleInventoryCloseFor(player);
    }

    public void onGuiItemClicked(Player player, int slot, ClickType clickType) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");
        if(actions[slot] == null)
            return;

        if(actionSounds[slot] != null)
            player.playSound(player, actionSounds[slot], 1.0f, 1.0f);

        GUIActionContext context = new GUIActionContext(player, this);
        GUIActionResult result = actions[slot].runCallback(context, clickType);
        if(result == GUIActionResult.Close)
            scheduleInventoryCloseFor(player);
    }

    private void fillInventoryWithEmptySlotItems() {
        for(int index = 0; index < backingInventory.getSize(); index++)
            backingInventory.setItem(index, EMPTY_INVENTORY_SLOT_ITEM);
    }

    private void scheduleInventoryCloseFor(Player player) {
        // NOTE: closing of the inventory must be scheduler for the next tick
        Bukkit.getScheduler().runTask(LPChestGUI.getInstance(), () -> closeInventoryFor(player));
    }

    private void scheduleInventoryReopenFor(Player player) {
        Bukkit.getScheduler().runTask(LPChestGUI.getInstance(), () -> {
            closeInventoryFor(player);
            Bukkit.getScheduler().runTask(LPChestGUI.getInstance(), () -> {
                openFor(player);
            });
        });
    }

    private void closeInventoryFor(Player player) {
        GUIManager guiManager = LPChestGUI.getInstance().getGUIManager();
        guiManager.removeGUIForPlayer(player);
        player.closeInventory();
    }

}
