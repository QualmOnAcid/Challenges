package challenges.challenges;

import challenges.challenges.Enums.TimerState;
import challenges.challenges.Utils.Timer.Timer;
import challenges.challenges.bukkit.commands.ResetCommand;
import challenges.challenges.bukkit.listener.TimerListeners;
import challenges.challenges.iChallenges.Challenges.Challenge;
import challenges.challenges.iChallenges.Challenges.ChallengeManager;
import challenges.challenges.iChallenges.Settings.Setting;
import challenges.challenges.iChallenges.Settings.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public final class Challenges extends JavaPlugin {

    private static Challenges instance;
    private static Timer timer;

    private static SettingsManager settingsManager;

    private static ChallengeManager challengeManager;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        saveConfig();
        if (getConfig().contains("reset.reset") && getConfig().getBoolean("reset.reset")) {
            deleteFolder("world");
            deleteFolder("world_nether");
            deleteFolder("world_the_end");
        }
        if(getConfig().contains("reset.reset") && getConfig().getBoolean("reset.reset")) {
            try {
                getConfig().set("reset.reset", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
           // Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        }
    }

    private void deleteFolder(String folder) {
        if(Files.exists(Paths.get(folder))) {
            try {
                Files.walk(Paths.get(folder)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {
        InitTimer();
        settingsManager = new SettingsManager();
        for(Setting s : settingsManager.getSettingsList()) {
            s.start();
        }
        challengeManager = new ChallengeManager();
        for(Challenge c : challengeManager.getChallengeList()) {
            c.start();
        }
        Bukkit.getPluginManager().registerEvents(new TimerListeners(), this);
        getCommand("reset").setExecutor(new ResetCommand());
        System.out.println(ChatColor.MAGIC + "[CHALLENGES] §f§lDas Plugin wurde aktiviert.");
    }

    @Override
    public void onDisable() {
        SaveTimer();
        for(Setting s : settingsManager.getSettingsList()) {
            s.stop();
        }
        for(Challenge c : challengeManager.getChallengeList()) {
            c.stop();
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

    public ChallengeManager getChallengeManager() {
        return challengeManager;
    }

}
