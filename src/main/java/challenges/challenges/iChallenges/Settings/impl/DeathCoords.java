package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathCoords extends Setting {

    public DeathCoords(String name, String description, Material guiMaterial, boolean firstValue) {
        super(name, description, guiMaterial, firstValue);
    }

    @Override
    public void start() {
        super.load();
        System.out.println("[" + name + "] Loaded successfully.");
    }

    @Override
    public void stop() {
        super.save();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(isEnabled) {
            Player p = (Player) e.getEntity();
            String worldName = p.getWorld().getName();
            switch (worldName) {
                case "world":
                    worldName = "World";
                    break;
                case "world_nether":
                    worldName = "Nether";
                    break;
                case "world_the_end":
                    worldName = "End";
                    break;
            }
            int x = p.getLocation().getBlockX();
            int y = p.getLocation().getBlockY();
            int z = p.getLocation().getBlockZ();
            e.setDeathMessage("§e§lChallenges §8§l» §f" + e.getDeathMessage() + " §7[§e" + x + "§7, §e" + y + "§7, §e" + z + "§7, §e" + worldName + "§7]");
        } else {
            e.setDeathMessage("§e§lChallenges §8§l» §f" + e.getDeathMessage());
        }
    }

}
