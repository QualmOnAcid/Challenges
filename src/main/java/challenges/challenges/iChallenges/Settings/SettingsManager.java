package challenges.challenges.iChallenges.Settings;

import challenges.challenges.Challenges;
import challenges.challenges.Utils.Utils;
import challenges.challenges.bukkit.listener.InventoryClickListener;
import challenges.challenges.iChallenges.Settings.impl.NoMoveOnTimer;
import challenges.challenges.iChallenges.Settings.impl.NoMoveOnTimerPaused;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class SettingsManager implements Listener {
    private ArrayList<Setting> settingsList = new ArrayList<Setting>();
    public int[] inventorySlots = { 10, 11, 12, 13, 14, 15, 16 };

    public SettingsManager() {
       // settingsList.add(new NoMoveOnTimer("No Move", "Dont Move", Material.LEATHER_BOOTS));
        settingsList.add(new NoMoveOnTimerPaused("Block Pregame Movement", "Wenn aktiv, können sich die Spieler nicht bewegen,\nsolange der Timer pausiert ist", Material.LEATHER_BOOTS, true));
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), Challenges.getInstance());
        Challenges.getInstance().saveConfig();
    }

    public ArrayList<Setting> getSettingsList() {
        return settingsList;
    }


    public Inventory getInventory(int _page) {
        ArrayList<Inventory> settingsInventories = new ArrayList<>();
        int page = 0;
        int item = 0;
        for(Setting s : getSettingsList()) {
            Inventory inventory;
            if(settingsInventories.isEmpty()) {
                inventory = createInv(page);
                settingsInventories.add(inventory);
            } else if(item >= inventorySlots.length) {
                item = 0;
                inventory = createInv(++page);
                settingsInventories.add(inventory);
            } else {
                inventory = settingsInventories.get(page);
            }
            inventory.setItem(inventorySlots[item], s.getGUIItem());
            item++;
        }
        return settingsInventories.get(_page);
    }

    public Inventory createInv(int page) {
        Inventory inv = Bukkit.createInventory(null, 3*9, "§e§lChallenges §8» §7Page " + ++page);
        Utils.FillInventoryWithMaterial(inv, Material.BLACK_STAINED_GLASS_PANE, 0);
        return inv;
    }


}
