package challenges.challenges.bukkit.listener;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class TimerListeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(Challenges.getTimer().getTimerState() == TimerState.PAUSED) e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(Challenges.getTimer().getTimerState() == TimerState.PAUSED && e.getPlayer().getGameMode() == GameMode.SURVIVAL) e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(Challenges.getTimer().getTimerState() == TimerState.PAUSED && e.getPlayer().getGameMode() == GameMode.SURVIVAL) e.setCancelled(true);
    }

    @EventHandler
    public void EntityTrigger(EntityTargetEvent e) {
        if(Challenges.getTimer().getTimerState() == TimerState.PAUSED) e.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if(Challenges.getTimer().getTimerState() == TimerState.PAUSED) e.setCancelled(true);
    }

}
