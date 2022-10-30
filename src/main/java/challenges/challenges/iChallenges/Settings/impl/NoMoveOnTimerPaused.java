package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoMoveOnTimerPaused extends Setting {

    public NoMoveOnTimerPaused(String name, String description, Material guiMaterial, boolean firstValue) {
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
    public void OnMove(PlayerMoveEvent e) {
        if(isEnabled) {
            if(Challenges.getTimer().getTimerState() == TimerState.PAUSED && e.getPlayer().getGameMode() == GameMode.SURVIVAL)
                e.setCancelled(true);
        }
    }

}
