package challenges.challenges.iChallenges.Challenges;

import challenges.challenges.Challenges;
import challenges.challenges.Utils.Heads.Heads;
import challenges.challenges.Utils.Items.ItemBuilder;
import challenges.challenges.Utils.Utils;
import challenges.challenges.bukkit.commands.SettingsCommand;
import challenges.challenges.iChallenges.Challenges.impl.HigherJump;
import challenges.challenges.iChallenges.Challenges.impl.IceFloor;
import challenges.challenges.iChallenges.Challenges.impl.MedusaChallenge;
import challenges.challenges.iChallenges.Settings.Setting;
import challenges.challenges.iChallenges.Settings.impl.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ChallengeManager  implements Listener {
    private ArrayList<Challenge> challengeList = new ArrayList<Challenge>();
    public int[] inventorySlots = { 10, 11, 12, 13, 14, 15, 16 };

    public ChallengeManager() {
        challengeList.add(new MedusaChallenge("Medusa", "Jedes Mob was von einem Spieler angeguckt wird, \nwird zu Stein verwandelt", Material.STONE, false));
        challengeList.add(new IceFloor("Eisboden", "Beim laufen entsteht eine 3x3 \ngroße Eisfläche unter allen Spielern", Material.PACKED_ICE, false));
        challengeList.add(new HigherJump("Höhere Sprünge", "Jeder Spieler springt bei jedem mal höher", Material.RABBIT_FOOT, false));
        Bukkit.getPluginManager().registerEvents(this, Challenges.getInstance());
        Challenges.getInstance().getCommand("settings").setExecutor(new SettingsCommand());
        Challenges.getInstance().saveConfig();
    }

    public ArrayList<Challenge> getChallengeList() {
        return challengeList;
    }

    public Challenge getChallengeByName(String name) {
        Challenge s = null;

        for(Challenge c : getChallengeList()) {
            if(c.getName().equalsIgnoreCase(name)) s = c;
        }

        return s;
    }

    public Inventory getInventory(int _page) {
        ArrayList<Inventory> settingsInventories = new ArrayList<>();
        int page = 0;
        int item = 0;
        int oldItem = 0;
        for(Challenge s : getChallengeList()) {
            Inventory inventory;
            boolean nextArrow = false;
            if(settingsInventories.isEmpty()) {
                inventory = createInv(page);
                settingsInventories.add(inventory);
            } else if(item >= inventorySlots.length) {
                inventory = settingsInventories.get(page);
                nextArrow = ++oldItem > inventorySlots.length;
                if(nextArrow) {
                    inventory.setItem(3*9 - 1, Heads.Next());
                }
                item = 0;
                oldItem = 0;
                inventory = createInv(++page);
                settingsInventories.add(inventory);
            } else {
                inventory = settingsInventories.get(page);
            }
            inventory.setItem(inventorySlots[item], s.getGUIItem());
            item++;
            oldItem++;
            inventory.setItem(2*9, Heads.Back());
        }
        return settingsInventories.get(_page);
    }

    public Inventory createInv(int page) {
        Inventory inv = Bukkit.createInventory(null, 3*9, "§e§lChallenges §8» §7Page " + ++page);
        Utils.FillInventoryWithMaterial(inv, Material.BLACK_STAINED_GLASS_PANE);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getWhoClicked() instanceof Player) {
            if(e.getView().getTitle().startsWith("§e§lChallenges §8» §7Page")) {
                Player p = (Player) e.getWhoClicked();
                int slot = e.getRawSlot();
                e.setCancelled(true);
                if(e.getCurrentItem() != null) {
                    boolean isItemChallenge = false;
                    Challenge currentSetting = null;
                    int currentPage = 0;
                    currentPage = Integer.parseInt(e.getView().getTitle().replace("§e§lChallenges §8» §7Page ", ""));
                    currentPage--;
                    for(Challenge s : getChallengeList()) {
                        if(s.getGUIItem().getItemMeta().getDisplayName().equalsIgnoreCase(e.getCurrentItem().getItemMeta().getDisplayName())) {
                            isItemChallenge = true;
                            currentSetting = s;
                            break;
                        }
                    }
                    if(isItemChallenge && currentSetting != null) {
                        currentSetting.isEnabled = !currentSetting.isEnabled;
                        currentSetting.save();
                        p.openInventory(getInventory(currentPage));
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        for(Player a : Bukkit.getOnlinePlayers()) {
                            if(Challenges.getInstance().getSettingsManager().getSettingByName("Setting Title").isEnabled()) {
                                if(currentSetting.isEnabled) {
                                    a.sendTitle(ChatColor.GREEN + currentSetting.getName(), "§7aktiviert", 10, 30, 10);
                                } else {
                                    a.sendTitle(ChatColor.RED + currentSetting.getName(), "§7deaktiviert", 10, 30, 10);
                                }
                            }
                        }
                    } else {
                        switch (slot) {

                            case 2*9:
                                if(currentPage == 0) {
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
                                } else {
                                    currentPage--;
                                    p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, 5);
                                    p.openInventory(getInventory(currentPage));
                                }
                                break;
                            case 3*9-1:
                                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {
                                    currentPage++;
                                    p.openInventory(getInventory(currentPage));
                                    p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, 5);
                                }
                                break;

                            default: break;
                        }
                    }
                }
            }
        }
    }


}
