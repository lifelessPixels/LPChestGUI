package pl.lifelesspixels.lpchestgui;

import org.bukkit.plugin.java.JavaPlugin;
import pl.lifelesspixels.lpchestgui.data.GUIManager;
import pl.lifelesspixels.lpchestgui.events.InventoryEventsListener;

public class LPChestGUI extends JavaPlugin {

    private static LPChestGUI instance;
    private GUIManager GUIManager;

    @Override
    public void onEnable() {
        instance = this;

        // create data storage
        GUIManager = new GUIManager();

        // register event handlers
        getServer().getPluginManager().registerEvents(new InventoryEventsListener(), this);
    }

    public GUIManager getGUIManager() {
        return GUIManager;
    }

    public static LPChestGUI getInstance() {
        return instance;
    }

}
