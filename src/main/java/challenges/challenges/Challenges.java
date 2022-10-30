package challenges.challenges;

import challenges.challenges.Enums.TimerState;
import challenges.challenges.Utils.Timer.Timer;
import challenges.challenges.iChallenges.Settings.Setting;
import challenges.challenges.iChallenges.Settings.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Challenges extends JavaPlugin {

    private static Challenges instance;
    private static Timer timer;

    private static SettingsManager settingsManager;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        InitTimer();
        settingsManager = new SettingsManager();
        for(Setting s : settingsManager.getSettingsList()) {
            s.start();
        }
        System.out.println(ChatColor.MAGIC + "[CHALLENGES] §f§lDas Plugin wurde aktiviert.");
    }

    @Override
    public void onDisable() {
        SaveTimer();
        for(Setting s : settingsManager.getSettingsList()) {
            s.stop();
        }
        saveConfig();
        System.out.println("§c§l[CHALLENGES] §f§lDas Plugin wurde deaktiviert.");
    }

    public void InitTimer() {
        int currentTime = getConfig().getInt("timer.time");
        boolean reversed = getConfig().getBoolean("timer.reversed");
        timer = new Timer(currentTime, TimerState.PAUSED, reversed);
    }

    public void SaveTimer() {
        getConfig().set("timer.time", getTimer().getCurrentTime());
        getConfig().set("timer.reversed", getTimer().isReversed());
    }

    public static Challenges getInstance() {
        return instance;
    }

    public static Timer getTimer() {
        return timer;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

}
