package pl.lifelesspixels.lpchestgui.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import pl.lifelesspixels.lpchestgui.LPChestGUI;
import pl.lifelesspixels.lpchestgui.data.ClickType;
import pl.lifelesspixels.lpchestgui.data.GUIManager;
import pl.lifelesspixels.lpchestgui.gui.BaseGUI;
import pl.lifelesspixels.lpchestgui.gui.ChestGUI;

public class InventoryEventsListener implements Listener {

    @EventHandler
    public void onInventoryItemClicked(InventoryClickEvent event) {
        if(event.getSlot() < 0)
            return;

        GUIManager guiManager = LPChestGUI.getInstance().getGUIManager();
        Player player = (Player)(event.getWhoClicked());

        BaseGUI gui = guiManager.getGUIForPlayer(player);
        if(gui == null)
            return;

        ClickType clickType;
        switch (event.getClick()) {
            case LEFT -> clickType = ClickType.Left;
            case RIGHT -> clickType = ClickType.Right;
            case SHIFT_LEFT -> clickType = ClickType.ShiftLeft;
            case SHIFT_RIGHT -> clickType = ClickType.ShiftRight;
            default -> {
                event.setCancelled(true);
                return;
            }
        }

        int slot = event.getSlot();
        if (!gui.isBackingInventory(event.getClickedInventory())) {
            gui.onPlayerItemClicked(player, slot, clickType);
        } else gui.onGUIItemClicked(player, slot, clickType);
        event.setCancelled(true);

    }

    @EventHandler
    public void onInventoryClosed(InventoryCloseEvent event) {
        GUIManager guiManager = LPChestGUI.getInstance().getGUIManager();
        Player player = (Player)(event.getPlayer());
        BaseGUI gui = guiManager.getGUIForPlayer(player);

        if(gui != null) {
            gui.onInventoryClosed(player);
            guiManager.removeGUIForPlayer(player);
        }
    }

}
