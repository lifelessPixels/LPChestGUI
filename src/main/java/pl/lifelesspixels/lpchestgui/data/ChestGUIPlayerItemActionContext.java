package pl.lifelesspixels.lpchestgui.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.lifelesspixels.lpchestgui.gui.ChestGUI;

public class ChestGUIPlayerItemActionContext {

    private final Player player;
    private final int slot;
    private final ClickType clickType;
    private final ChestGUI gui;

    public ChestGUIPlayerItemActionContext(Player player, int slot, ClickType clickType, ChestGUI gui) {
        this.player = player;
        this.slot = slot;
        this.clickType = clickType;
        this.gui = gui;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSlot() {
        return slot;
    }

    public PlayerInventory getPlayerInventory() {
        return player.getInventory();
    }

    public ItemStack getItem() {
        return getPlayerInventory().getItem(slot);
    }

    public ClickType getClickType() {
        return clickType;
    }

    public ChestGUI getGui() {
        return gui;
    }

}
