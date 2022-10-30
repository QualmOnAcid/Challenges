package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.Challenges;
import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GlowSetting extends Setting {

    public GlowSetting(String name, String description, Material guiMaterial, boolean firstValue) {
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
            if(Bukkit.getOnlinePlayers().size() > 0) {
                for(Player a : Bukkit.getOnlinePlayers()) {
                    a.setGlowing(isEnabled);
                }
            }
        }, 0, 20);
    }

}
