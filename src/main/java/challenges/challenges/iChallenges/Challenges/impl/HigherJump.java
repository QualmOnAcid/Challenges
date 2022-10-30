package challenges.challenges.iChallenges.Challenges.impl;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.bukkit.events.PlayerJumpEvent;
import challenges.challenges.iChallenges.Challenges.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HigherJump extends Challenge {

    public HigherJump(String name, String description, Material guiMaterial, boolean firstValue) {
        super(name, description, guiMaterial, firstValue);
    }

    private LinkedHashMap<String, Integer> playerJumps = new LinkedHashMap<>();

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
        Challenges.getInstance().getConfig().set("challenges." + getName() + ".players", null);
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        if(isEnabled() && Challenges.getTimer().getTimerState() == TimerState.RUNNING && e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                int jumps = getJumps(e.getPlayer());
                float y = jumps / 7f;
                e.getPlayer().setVelocity(new Vector().setY(y));

        }
    }

    public int getJumps(Player p) {
        String UUID = p.getUniqueId().toString();
        int pJumps = 0;
        List<String> configPlayers = Challenges.getInstance().getConfig().getStringList("challenges." + getName() + ".players");
        for(String i : configPlayers) {
            String iUUID = i.split("|")[0];
            int iJumps = Integer.parseInt(i.split("|")[1]);
            if(iUUID == UUID) {
                pJumps = iJumps;
            }
        }
        if(!playerJumps.containsKey(UUID))
            playerJumps.put(UUID, pJumps);
        int jumps = playerJumps.get(UUID);
        jumps++;
        playerJumps.put(UUID, jumps);
        for(String i : configPlayers) {
            String iUUID = i.split("|")[0];
            if(iUUID == UUID) {
                i = UUID + "|" + jumps;
            }
        }
        Challenges.getInstance().getConfig().set("challenges." + getName() + ".players", configPlayers);
        Challenges.getInstance().saveConfig();
        return jumps;
    }

}