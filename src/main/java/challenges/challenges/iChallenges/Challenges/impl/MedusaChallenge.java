package challenges.challenges.iChallenges.Challenges.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Challenges.Challenge;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MedusaChallenge extends Challenge {

    public MedusaChallenge(String name, String description, Material guiMaterial, boolean firstValue) {
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

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(isEnabled && Challenges.getTimer().getTimerState() == TimerState.RUNNING && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            Player p = (Player) e.getPlayer();
            for(Entity es : p.getNearbyEntities(100, 100, 100)) {
                if(es.getType().isAlive()) {
                    if(es.getType() != EntityType.PLAYER && es.getType() != EntityType.ARMOR_STAND) {
                        LivingEntity livingEntity = (LivingEntity) es;
                        if(isLookingAtEntity(p, livingEntity, 100)) {

                                Location loc = p.getLocation();
                                Material material = Material.AIR;
                                while (!material.isSolid()) {
                                    int randX = ThreadLocalRandom.current().nextInt(loc.getBlockX() - 20, loc.getBlockX() + 20);
                                    int randY = ThreadLocalRandom.current().nextInt(20, 70);
                                    int randZ = ThreadLocalRandom.current().nextInt(loc.getBlockZ() - 20, loc.getBlockZ() + 20);

                                    material = p.getWorld().getBlockAt(randX, randY, randZ).getType();
                                }
                                es.getWorld().getBlockAt(es.getLocation().getBlockX(), es.getLocation().getBlockY(), es.getLocation().getBlockZ()).setType(material);
                                p.playSound(loc, Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);

                                es.remove();

                        }
                    }
                }
            }
        }
    }

    public boolean isLookingAtEntity(final Player player, final LivingEntity a, final int range) {
        final Location eye = player.getEyeLocation();
        final Vector toEntity = a.getEyeLocation().toVector().subtract(eye.toVector());
        final double dot = toEntity.normalize().dot(eye.getDirection());
        final ArrayList<Block> blocksInSight = (ArrayList<Block>)player.getLineOfSight((Set)null, range);
        final ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0; i < blocksInSight.size(); ++i) {
            sight.add(blocksInSight.get(i).getLocation());
        }
        final int x = a.getLocation().getBlockX();
        final int y = a.getLocation().getBlockY();
        final int z = a.getLocation().getBlockZ();
        for (int j = 0; j < sight.size(); ++j) {
            if (Math.abs(x - sight.get(j).getX()) < 1.3 && Math.abs(y - sight.get(j).getY()) < 1.5 && Math.abs(z - sight.get(j).getZ()) < 1.3) {
                return dot > 0.99;
            }
        }
        return false;
    }

}
