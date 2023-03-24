package pl.lifelesspixels.lpchestgui.gui;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.lifelesspixels.lpchestgui.data.*;

import java.util.Objects;
import java.util.function.Consumer;

public class ChestGUI extends BaseGUI {

    private final int rows;
    private final ChestGUIClickAction[] actions;
    private final Sound[] actionSounds;
    private final Inventory backingInventory;
    private Consumer<ChestGUIPlayerItemActionContext> playerItemClickedHandler = null;

    public ChestGUI(int rows) {
        if(rows < 1 || rows > 6)
            throw new IllegalArgumentException("inventory rows must be at least 0 and at most 6");

        this.rows = rows;
        this.actions = new ChestGUIClickAction[rows * 9];
        this.actionSounds = new Sound[rows * 9];
        this.backingInventory = Bukkit.createInventory(null, rows * 9);
        fillInventoryWithEmptySlotItems(this.backingInventory);
    }

    public ChestGUI(int rows, String title) {
        if(rows < 1 || rows > 6)
            throw new IllegalArgumentException("inventory rows must be at least 0 and at most 6");

        this.rows = rows;
        this.actions = new ChestGUIClickAction[rows * 9];
        this.actionSounds = new Sound[rows * 9];
        this.backingInventory = Bukkit.createInventory(null, rows * 9, title);
        fillInventoryWithEmptySlotItems(this.backingInventory);
    }

    public void setAction(int slot, ChestGUIClickAction action, ItemStack item) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");
        if(!Objects.requireNonNull(backingInventory.getItem(slot)).isSimilar(EMPTY_INVENTORY_SLOT_ITEM))
            throw new IllegalArgumentException("cannot set action for non-empty slot");

        actions[slot] = action;
        backingInventory.setItem(slot, item);
    }

    public void setAction(int slot, ChestGUIClickAction action, ItemStack item, Sound actionSound) {
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

    public void setPlayerItemClickedHandler(Consumer<ChestGUIPlayerItemActionContext> handler) {
        playerItemClickedHandler = handler;
    }

    public void resetSlot(int slot) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");

        actions[slot] = null;
        actionSounds[slot] = null;
        backingInventory.setItem(slot, EMPTY_INVENTORY_SLOT_ITEM);
    }

    @Override
    protected Inventory getBackingInventory() {
        return backingInventory;
    }

    @Override
    public void onGUIItemClicked(Player player, int slot, ClickType clickType) {
        if(slot < 0 || slot > actions.length)
            throw new IllegalArgumentException("slot must be in valid range for the backing inventory");
        if(actions[slot] == null)
            return;

        if(actionSounds[slot] != null)
            player.playSound(player, actionSounds[slot], 1.0f, 1.0f);

        ChestGUIClickActionContext context = new ChestGUIClickActionContext(player, this);
        ChestGUIClickActionResult result = actions[slot].runCallback(context, clickType);
        if(result == ChestGUIClickActionResult.Close)
            closeGUIFor(player);
    }

    @Override
    public void onPlayerItemClicked(Player player, int slot, ClickType clickType) {
        Inventory inventory = player.getInventory();
        if(slot < 0 || slot >= inventory.getSize())
            throw new IllegalArgumentException("slot must be in valid range for the player's inventory");

        if(inventory.getItem(slot) == null || playerItemClickedHandler == null)
            return;

        ChestGUIPlayerItemActionContext context = new ChestGUIPlayerItemActionContext(player, slot, clickType, this);
        playerItemClickedHandler.accept(context);
    }

}
