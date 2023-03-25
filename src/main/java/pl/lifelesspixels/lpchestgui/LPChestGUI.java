package pl.lifelesspixels.lpchestgui;

import org.bukkit.plugin.java.JavaPlugin;
import pl.lifelesspixels.lpchestgui.data.GUIManager;
import pl.lifelesspixels.lpchestgui.events.InventoryEventsListener;

public class LPChestGUI extends JavaPlugin {

    private static LPChestGUI instance;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        instance = this;

        // create data storage
        guiManager = new GUIManager();

        // register event handlers
        getServer().getPluginManager().registerEvents(new InventoryEventsListener(), this);
    }

    @Override
    public void onDisable() {
        // close all open GUIs
        guiManager.closeAllCurrentlyOpenedGUIs();
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }

    public static LPChestGUI getInstance() {
        return instance;
    }

}
