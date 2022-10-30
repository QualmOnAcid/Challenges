package challenges.challenges.iChallenges.Settings;

import challenges.challenges.Challenges;
import challenges.challenges.Utils.Heads.Heads;
import challenges.challenges.Utils.Items.ItemBuilder;
import challenges.challenges.Utils.Utils;
import challenges.challenges.bukkit.commands.SettingsCommand;
import challenges.challenges.bukkit.listener.InventoryClickListener;
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

public class SettingsManager implements Listener {
    private ArrayList<Setting> settingsList = new ArrayList<Setting>();
    public int[] inventorySlots = { 10, 11, 12, 13, 14, 15, 16 };

    public SettingsManager() {
       // settingsList.add(new NoMoveOnTimer("No Move", "Dont Move", Material.LEATHER_BOOTS));
        settingsList.add(new NoMoveOnTimerPaused("Block Pregame Movement", "Wenn aktiv, können sich die Spieler nicht bewegen,\nsolange der Timer pausiert ist", Material.LEATHER_BOOTS, true));
        settingsList.add(new EndParticlesOnTimerPaused("End Partikel", "Wenn aktiv, spawnen Enderpartikel bei allen \nSpielern, solange der Timer pausiert ist", Material.ENDER_EYE, false));
        settingsList.add(new DamageInChat("Schaden in Chat", "Sobald ein Spieler Schaden bekommt, erscheint dies im Chat", Material.NAME_TAG, true));
        settingsList.add(new CutClean("Cut Clean", "Resourcen und Essen muss nicht \nmehr gebraten werden", Material.SHEARS, false));
        settingsList.add(new SettingChangeTitle("Setting Title", "Sobald sich ein Status eines Settings ändert, \nwerden alle Spieler benachrichtigt", Material.OAK_SIGN, true));
        settingsList.add(new GlowSetting("Spieler Glow", "Alle Spieler besitzen einen Glow Effekt", Material.SPECTRAL_ARROW, false));
        settingsList.add(new DeathCoords("Koordinaten bei Tod", "Wenn ein Spieler stribt, werden seine Koordinaten\nfür alle Spieler im Chat sichtbar", Material.SKELETON_SKULL, true));
        settingsList.add(new SharedDamage("Geteilte Herzen", "Sobald ein Spieler Schaden erleidet, erleiden alle\nanderen Spieler den gleichen Schaden", Material.FERMENTED_SPIDER_EYE, false));
        Bukkit.getPluginManager().registerEvents(this, Challenges.getInstance());
        Challenges.getInstance().getCommand("settings").setExecutor(new SettingsCommand());
        Challenges.getInstance().saveConfig();
    }

    public ArrayList<Setting> getSettingsList() {
        return settingsList;
    }

    public Setting getSettingByName(String name) {
        Setting s = null;

        for(Setting setting : getSettingsList()) {
            if(setting.getName().equalsIgnoreCase(name)) s = setting;
        }

        return s;
    }

    public Inventory getInventory(int _page) {
        ArrayList<Inventory> settingsInventories = new ArrayList<>();
        int page = 0;
        int item = 0;
        int oldItem = 0;
        for(Setting s : getSettingsList()) {
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
        Inventory inv = Bukkit.createInventory(null, 3*9, "§e§lSettings §8» §7Page " + ++page);
        Utils.FillInventoryWithMaterial(inv, Material.BLACK_STAINED_GLASS_PANE);
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getWhoClicked() instanceof Player) {
            if(e.getView().getTitle().equalsIgnoreCase("§e§lChallenges §8§l» §7Menü")) {
                Player p = (Player) e.getWhoClicked();
                int slot = e.getRawSlot();
                e.setCancelled(true);
                switch (slot) {
                    case 16:
                        Inventory inv = Challenges.getTimer().getTimerGUI().getTimerInventoryWithArrow();;
                        p.openInventory(inv);
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        break;
                    case 14:
                        Inventory invb = Challenges.getInstance().getSettingsManager().getInventory(0);
                        p.openInventory(invb);
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        break;
                    case 10:
                        Inventory invc = Challenges.getInstance().getChallengeManager().getInventory(0);
                        p.openInventory(invc);
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        break;
                    default:
                        break;
                }
            } else if(e.getView().getTitle().startsWith("§e§lSettings §8» §7Page")) {
                Player p = (Player) e.getWhoClicked();
                int slot = e.getRawSlot();
                e.setCancelled(true);
                if(e.getCurrentItem() != null) {
                    boolean isItemChallenge = false;
                    Setting currentSetting = null;
                    int currentPage = 0;
                    currentPage = Integer.parseInt(e.getView().getTitle().replace("§e§lSettings §8» §7Page ", ""));
                    currentPage--;
                    for(Setting s : getSettingsList()) {
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
                            if(getSettingByName("Setting Title").isEnabled) {
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
