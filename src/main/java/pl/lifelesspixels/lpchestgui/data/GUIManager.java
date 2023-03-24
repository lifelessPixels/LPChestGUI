package pl.lifelesspixels.lpchestgui.data;

import org.bukkit.entity.Player;
import pl.lifelesspixels.lpchestgui.gui.ChestGUI;

import java.util.HashMap;

public class GUIManager {

    private final HashMap<Player, ChestGUI> openedGUIs = new HashMap<>();

    public void setGUIForPlayer(Player player, ChestGUI gui) {
        openedGUIs.put(player, gui);
    }

    public ChestGUI getGUIForPlayer(Player player) {
        return openedGUIs.get(player);
    }

    public void removeGUIForPlayer(Player player) {
        openedGUIs.remove(player);
    }

    public boolean hasOpenGUI(Player player) {
        return openedGUIs.containsKey(player);
    }

}