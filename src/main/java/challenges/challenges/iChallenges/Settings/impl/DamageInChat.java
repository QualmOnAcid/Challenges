package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.DecimalFormat;

public class DamageInChat extends Setting {

    public DamageInChat(String name, String description, Material guiMaterial, boolean firstValue) {
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
    public void onDamage(EntityDamageEvent e) {
        if(isEnabled && Challenges.getTimer().getTimerState() == TimerState.RUNNING) {
            if(e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                final Double dmg = e.getFinalDamage() / 2.0;
                if (dmg > 0.0) {
                    String DamageCause;
                    if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        DamageCause = "Explosion";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                        DamageCause = "Kontakt";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                        DamageCause = "Cramming";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
                        DamageCause = "Drachenatem";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                        DamageCause = "Ertrinken";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        DamageCause = "Explosion";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL || e.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                        DamageCause = "Fallschaden";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                        DamageCause = "Feuer";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                        DamageCause = "Hot Floor";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                        DamageCause = "Lava";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                        DamageCause = "Blitz";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                        DamageCause = "Magie";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.MELTING) {
                        DamageCause = "Schmelzen";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.POISON) {
                        DamageCause = "Vergiftung";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        DamageCause = "Projektil";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                        DamageCause = "Hunger";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                        DamageCause = "Erstickung";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                        DamageCause = "Dornen";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                        DamageCause = "Void";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.WITHER) {
                        DamageCause = "Wither";
                    } else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                        DamageCause = "Attacke";
                    } else {
                        DamageCause = "Unbekannt";
                    }
                    String sdmg = new DecimalFormat("#.##").format(dmg);
                    if(dmg == 1.0) {
                        Bukkit.broadcastMessage("§e§lChallenges §7» §e§l" + p.getDisplayName() + " §fhat §e§l" + sdmg + "❤ §fSchaden erlitten. §8[§e§l" + DamageCause + "§8]");
                    } else {
                        Bukkit.broadcastMessage("§e§lChallenges §7» §e§l" + p.getDisplayName() + " §fhat §e§l" + sdmg + "❤ §fSchaden erlitten. §8[§e§l" + DamageCause + "§8]");
                    }
                }
            }
        }
    }

}
