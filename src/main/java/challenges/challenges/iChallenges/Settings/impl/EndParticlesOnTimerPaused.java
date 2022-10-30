package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class EndParticlesOnTimerPaused extends Setting {

    public EndParticlesOnTimerPaused(String name, String description, Material guiMaterial, boolean firstValue) {
        super(name, description, guiMaterial, firstValue);
    }

    @Override
    public void start() {
        super.load();
        System.out.println("[" + name + "] Loaded successfully.");
        Run();
    }

    @Override
    public void stop() {
        super.save();
    }

    public void Run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Challenges.getInstance(), () -> {
            if(isEnabled && Challenges.getTimer().getTimerState() == TimerState.PAUSED) {
                for(Player a : Bukkit.getOnlinePlayers())
                    if(a.getGameMode() == GameMode.SURVIVAL)
                        a.getWorld().spawnParticle(Particle.PORTAL, a.getLocation().add(0, 1, 0), 50);
            }
        }, 0, 5);
    }

}
