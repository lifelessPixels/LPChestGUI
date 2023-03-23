package pl.lifelesspixels.lpchestgui.data;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.lifelesspixels.lpchestgui.gui.ChestGUI;

public class GUIActionContext {

    private final Player player;
    private final ChestGUI gui;

    public GUIActionContext(Player player, ChestGUI gui) {
        this.player = player;
        this.gui = gui;
    }

    public Player getPlayer() {
        return player;
    }

    public ChestGUI getGUI() {
        return gui;
    }
}
