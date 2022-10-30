package challenges.challenges.bukkit.listener;

import challenges.challenges.Challenges;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getWhoClicked() instanceof Player) {
            if(e.getView().getTitle().equalsIgnoreCase("§e§lChallenges §8§l» §7Menü")) {
                Player p = (Player) e.getWhoClicked();
                int slot = e.getRawSlot();
                switch (slot) {
                    case 16:
                        p.openInventory(Challenges.getTimer().getTimerGUI().getTimerInventory());
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        break;
                    case 14:
                        p.openInventory(Challenges.getInstance().getSettingsManager().getInventory(0));
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
