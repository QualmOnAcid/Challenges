package challenges.challenges.bukkit.commands;

import challenges.challenges.Utils.Items.ItemBuilder;
import challenges.challenges.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Du musst dafür ein Spieler sein!");
            return false;
        }
        Player p = (Player) sender;
        Inventory settingsInventory = Bukkit.createInventory(null, 9*3, "§e§lChallenges §8§l» §7Menü");
        Utils.FillInventoryWithMaterial(settingsInventory, Material.BLACK_STAINED_GLASS_PANE);

        ItemStack challengeStack = new ItemBuilder(Material.EMERALD)
                .setDisplayName("§8§l» §e§lChallenges")
                .build();

        ItemStack goalStack = new ItemBuilder(Material.HOPPER)
                .setDisplayName("§8§l» §e§lZiele")
                .build();

        ItemStack settingsStack = new ItemBuilder(Material.BEACON)
                .setDisplayName("§8§l» §e§lSettings")
                .build();

        ItemStack timerStack = new ItemBuilder(Material.CLOCK)
                .setDisplayName("§8§l» §e§lTimer")
                .build();

        settingsInventory.setItem(10, challengeStack);
        settingsInventory.setItem(12, goalStack);
        settingsInventory.setItem(14, settingsStack);
        settingsInventory.setItem(16, timerStack);


        p.openInventory(settingsInventory);
        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, 5);
        return false;
    }
}
