package challenges.challenges.iChallenges.Challenges.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Challenges.Challenge;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;

public class IceFloor extends Challenge {

    public IceFloor(String name, String description, Material guiMaterial, boolean firstValue) {
        super(name, description, guiMaterial, firstValue);
    }

    @Override
    public void start() {
        super.load();
    }

    @Override
    public void stop() {
        super.save();
    }

    @Override
    public void reset() {

    }

    private ArrayList<Player> hasIceFloorActive = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if ( isEnabled && Challenges.getTimer().getTimerState() == TimerState.RUNNING && hasIceFloorActive.contains(e.getPlayer())) {
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    final Block block = e.getTo().getBlock().getRelative(x, -1, z);
                    if (block.getType().isAir()) {
                        block.setType(Material.PACKED_ICE);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if(e.isSneaking() && isEnabled && Challenges.getTimer().getTimerState() == TimerState.RUNNING) {
            if(hasIceFloorActive.contains(e.getPlayer())) {
                hasIceFloorActive.remove(e.getPlayer());
            } else {
                hasIceFloorActive.add(e.getPlayer());
            }
        }
    }

}
