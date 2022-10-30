package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CutClean extends Setting {

    public CutClean(String name, String description, Material guiMaterial, boolean firstValue) {
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
    public void onBreak(BlockBreakEvent e) {
        if(isEnabled && Challenges.getTimer().getTimerState() == TimerState.RUNNING) {
            Player p = (Player) e.getPlayer();
            if(p.getGameMode() != GameMode.SURVIVAL) return;
            if(e.getBlock().getType() == Material.IRON_ORE && e.getPlayer().getItemInHand() != null) {
                e.setDropItems(false);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
            }
            if(e.getBlock().getType() == Material.GOLD_ORE && e.getPlayer().getItemInHand() != null) {
                e.setDropItems(false);
                e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(isEnabled && Challenges.getTimer().getTimerState() == TimerState.RUNNING) {
            if(e.getEntity() instanceof Cow) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_BEEF, 2));
                e.getDrops().add(new ItemStack(Material.LEATHER));
            } else if(e.getEntity() instanceof Pig) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_PORKCHOP, 2));
            } else if(e.getEntity() instanceof Chicken) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_CHICKEN, 2));
            } else if(e.getEntity() instanceof Salmon) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_SALMON, 2));
            } else if(e.getEntity() instanceof Cod) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_COD, 2));
            } else if(e.getEntity() instanceof Creeper) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.GUNPOWDER, 2));
            } else if(e.getEntity() instanceof Sheep) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_MUTTON, 2));
            } else if(e.getEntity() instanceof Rabbit) {
                e.getDrops().clear();
                e.getDrops().add(new ItemStack(Material.COOKED_RABBIT, 2));
            }
        }
    }

}
