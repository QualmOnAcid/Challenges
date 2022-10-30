package challenges.challenges.Utils.Timer;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.Utils.Items.ItemBuilder;
import challenges.challenges.Utils.Utils;
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

import java.util.*;

public class TimerGUI implements Listener {

    private Inventory timerInventory;
    private LinkedHashMap<String, Material> timerColors = new LinkedHashMap<>();


    public TimerGUI() {
        timerInventory = Bukkit.createInventory(null, 9*3, Challenges.getInstance().getConfig().getString("timer.gui.title"));
        timerColors.put("§0§l", Material.BLACK_DYE);
        timerColors.put("§2§l", Material.GREEN_DYE);
        timerColors.put("§4§l", Material.RED_DYE);
        timerColors.put("§6§l", Material.ORANGE_DYE);
        timerColors.put("§8§l", Material.GRAY_DYE);
        timerColors.put("§a§l", Material.LIME_DYE);
        timerColors.put("§c§l", Material.RED_DYE);
        timerColors.put("§e§l", Material.YELLOW_DYE);
        timerColors.put("§1§l", Material.BLUE_DYE);
        timerColors.put("§3§l", Material.CYAN_DYE);
        timerColors.put("§5§l", Material.PURPLE_DYE);
        timerColors.put("§7§l", Material.LIGHT_GRAY_DYE);
        timerColors.put("§9§l", Material.BLUE_DYE);
        timerColors.put("§b§l", Material.LIGHT_BLUE_DYE);
        timerColors.put("§d§l", Material.PINK_DYE);
        timerColors.put("§f§l", Material.WHITE_DYE);
    }

    public String getKeyByIndex(int index) {
        String output = "";
        int l = 0;
        for(Map.Entry<String, Material> entry : timerColors.entrySet()) {
            if(l == index) {
                output = entry.getKey();
            }
            l++;
        }
        return output;
    }

    private void Build() {
        Utils.FillInventoryWithMaterial(timerInventory, Material.BLACK_STAINED_GLASS_PANE, 0);
        ItemStack stateItem = new ItemStack(Material.RED_DYE);
        ItemStack reverseItem = new ItemStack(Material.ARROW);
        if(Challenges.getTimer().getTimerState() == TimerState.RUNNING) {
            stateItem = new ItemBuilder(Material.LIME_DYE).setGlow(false).setDisplayName("§8» §fTimer pausieren").setLore("§8• Klicke um den Timer zu pausieren").build();
        } else {
            stateItem = new ItemBuilder(Material.RED_DYE).setGlow(false).setDisplayName("§8» §fTimer fortsetzen").setLore("§8• Klicke um den Timer fortzusetzen").build();
        }
        if(Challenges.getTimer().isReversed()) {
            reverseItem = new ItemBuilder(Material.SPECTRAL_ARROW).setGlow(false).setDisplayName("§8» §fTimer zählt runter").setLore("§8• Klicke um den Timer umzukehren").build();
        } else {
            reverseItem = new ItemBuilder(Material.ARROW).setGlow(false).setDisplayName("§8» §fTimer zählt hoch").setLore("§8• Klicke um den Timer umzukehren").build();
        }
        ItemStack colorItem = getColorStack();
        timerInventory.setItem(13, colorItem);
        timerInventory.setItem(11, stateItem);
        timerInventory.setItem(15, reverseItem);
    }

    public Inventory getTimerInventory() {
        Build();
        return timerInventory;
    }

    public Inventory getTimerInventoryDontBuild() {
        return timerInventory;
    }

    public void setTimerInventory(Inventory timerInventory) {
        this.timerInventory = timerInventory;
    }

    public ItemStack getColorStack() {
        ItemStack output = new ItemStack(Material.GLASS);

        String currentConfigPrefix = Challenges.getInstance().getConfig().getString("timer.messages.timer-prefix");
        String items = "§6§l";
        Material itemMat = Material.BLACK_DYE;
        for(Map.Entry<String, Material> entry : timerColors.entrySet()) {
            Material key = entry.getValue();
            String Value = entry.getKey();
            if(Value.equals(currentConfigPrefix)) {
                items = Value;
                itemMat = key;
            }
        }

        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add("§8• Klicke um die Farbe zu ändern");
        itemLore.add("§8• " + items.replace("§l", "") + "Aktuelle Farbe");

        output = new ItemBuilder(itemMat).setLore(itemLore).setDisplayName("§8» §fTimer Farbe").build();

        return output;
    }

    public ItemStack getColorStack(String Prefix) {
        ItemStack output = new ItemStack(Material.GLASS);

        String currentConfigPrefix = Prefix;
        String items = "§6§l";
        Material itemMat = Material.BLACK_DYE;
        for(Map.Entry<String, Material> entry : timerColors.entrySet()) {
            Material key = entry.getValue();
            String Value = entry.getKey();
            if(Value.equals(currentConfigPrefix)) {
                items = Value;
                itemMat = key;
            }
        }

        ArrayList<String> itemLore = new ArrayList<>();
        itemLore.add("§8• Klicke um die Farbe zu ändern");
        itemLore.add("§8• " + items.replace("§l", "") + "Aktuelle Farbe");

        output = new ItemBuilder(itemMat).setLore(itemLore).setDisplayName("§8» §fTimer Farbe").build();

        return output;
    }

    public int getColorIndex(String Prefix) {
        int output = 0;
        for(Map.Entry<String, Material> entry : timerColors.entrySet()) {
            Material Key = entry.getValue();
            String Value = entry.getKey();
            if(Value.equals(Prefix)) {
                output++;
                break;
            }
            output++;
            if(output > timerColors.size()) output = 0;
        }

        if(output > timerColors.size()) output = 0;

        return output;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(e.getClickedInventory() != null && e.getView().getTitle() == Challenges.getInstance().getConfig().getString("timer.gui.title")) {
            if(e.getWhoClicked() instanceof Player) {
                Player p = (Player) e.getWhoClicked();
                e.setCancelled(true);
                int slot = e.getRawSlot();
                switch (slot) {
                    case 11:
                    if(Challenges.getTimer().getTimerState() == TimerState.RUNNING) {
                        ItemStack stateItem = new ItemBuilder(Material.RED_DYE).setGlow(false).setDisplayName("§8» §fTimer fortsetzen").setLore("§8• Klicke um den Timer fortzusetzen").build();
                        Challenges.getTimer().setTimerState(TimerState.PAUSED);
                        getTimerInventoryDontBuild().setItem(11, stateItem);
                        p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-paused-cmd"));
                        for(Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle(ChatColor.YELLOW + "Timer", "pausiert", 20, 50, 20);
                        }
                    } else {
                        ItemStack stateItem = new ItemBuilder(Material.LIME_DYE).setGlow(false).setDisplayName("§8» §fTimer pausieren").setLore("§8• Klicke um den Timer zu pausieren").build();
                        Challenges.getTimer().setTimerState(TimerState.RUNNING);
                        getTimerInventoryDontBuild().setItem(11, stateItem);
                        p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-resumed-cmd"));
                        for(Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle(ChatColor.GREEN + "Timer", "fortgesetzt", 20, 50, 20);
                        }
                    }
                    p.openInventory(getTimerInventory());
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                    break;
                    case 13:
                        int k = getColorIndex( Challenges.getInstance().getConfig().getString("timer.messages.timer-prefix"));
                    //    if(k == 16) k = 0;
                        if(k == timerColors.size()) k = 0;
                        String j = getKeyByIndex(k);
                        ItemStack s = getColorStack(j);

                        getTimerInventoryDontBuild().setItem(13, s);
                        Challenges.getInstance().getConfig().set("timer.messages.timer-prefix", j);

                        p.openInventory(getTimerInventory());
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, 5);
                        break;
                    case 15:
                        if(Challenges.getTimer().isReversed()) {
                            ItemStack reverseItem = new ItemBuilder(Material.ARROW).setGlow(false).setDisplayName("§8» §fTimer zählt hoch").setLore("§8• Klicke um den Timer umzukehren").build();
                            Challenges.getTimer().setReversed(false);
                            getTimerInventoryDontBuild().setItem(15, reverseItem);
                            p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-reversed-cmd"));
                            for(Player a : Bukkit.getOnlinePlayers()) {
                                a.sendTitle(ChatColor.GREEN + "Timer", "zählt hoch", 20, 50, 20);
                            }
                        } else {
                            ItemStack reverseItem = new ItemBuilder(Material.SPECTRAL_ARROW).setGlow(false).setDisplayName("§8» §fTimer zählt runter").setLore("§8• Klicke um den Timer umzukehren").build();
                            Challenges.getTimer().setReversed(true);
                            getTimerInventoryDontBuild().setItem(15, reverseItem);
                            p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-reversed-cmd"));
                            for(Player a : Bukkit.getOnlinePlayers()) {
                                a.sendTitle(ChatColor.YELLOW + "Timer", "zählt runter", 20, 50, 20);
                            }
                        }
                        p.openInventory(getTimerInventory());
                        p.playSound(p.getLocation(), Sound.BLOCK_BAMBOO_BREAK, 3, -5);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
