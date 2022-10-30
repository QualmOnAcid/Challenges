package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoMoveOnTimer extends Setting {

    public int timer;
    public boolean a;

    public NoMoveOnTimer(String name, String description, Material guiMaterial) {
        super(name, description, guiMaterial, false);
        timer = 0;
        a = false;
        updateVars();
    }

    @Override
    public void start() {
        super.load();
        timer = Challenges.getInstance().getConfig().getInt("settings." + name + ".timer");
        a = Challenges.getInstance().getConfig().getBoolean("settings." + name + ".a");
        System.out.println("[" + name + "] Loaded successfully.");
    }

    @Override
    public void stop() {
        updateVars();
        super.save();
    }

    public void updateVars() {
        varsToSave.put("timer", timer);
        varsToSave.put("a", a);
    }

}
