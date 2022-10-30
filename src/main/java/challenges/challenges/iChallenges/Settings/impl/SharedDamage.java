package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class SharedDamage extends Setting {

    public SharedDamage(String name, String description, Material guiMaterial, boolean firstValue) {
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
            if (e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                final Double dmg = e.getFinalDamage() / 2.0;
                syncronizeHealth(p, dmg);
            }
        }
    }

    public void syncronizeHealth(final Player p, final double health) {
            for (final Player pp : Bukkit.getOnlinePlayers()) {
                if (pp != p && pp.getGameMode().equals((Object) GameMode.SURVIVAL)) {
                    if (health > pp.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
                        pp.setHealth(pp.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                    }
                    else if (health < 0.0) {
                        pp.setHealth(0.0);
                    }
                    else {
                        pp.setHealth(health);
                    }
                }
            }
    }

}
