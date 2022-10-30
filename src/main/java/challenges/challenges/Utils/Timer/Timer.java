package challenges.challenges.Utils.Timer;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Timer {

    private int currentTime;
    private TimerState timerState;
    private boolean reversed;
    private int scheduleTimer;
    private TimerGUI timerGUI;

    public Timer(int currentTime, TimerState timerState, boolean reversed) {
        this.currentTime = currentTime;
        this.timerState = timerState;
        this.reversed = reversed;
        timerGUI = new TimerGUI();
        Challenges.getInstance().getCommand("timer").setExecutor(new TimerCommand());
        Challenges.getInstance().getCommand("timer").setTabCompleter(new TimerTabCompletor());
        Bukkit.getPluginManager().registerEvents(new TimerGUI(), Challenges.getInstance());
        Run();
    }

    private void Run() {
        scheduleTimer = Bukkit.getScheduler().scheduleSyncRepeatingTask(Challenges.getInstance(), () -> {

            //send timer to players
            String timerPrefix = Challenges.getInstance().getConfig().getString("timer.messages.timer-prefix");
            if(getTimerState() == TimerState.RUNNING) {
                for(Player a : Bukkit.getOnlinePlayers()) {
                    a.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerPrefix + Challenges.getInstance().getConfig().getString("timer.messages.timer-running").replace("<time>", ConvertTimerTime(getCurrentTime()))));
                }
            } else if(getTimerState() == TimerState.PAUSED) {
                for(Player a : Bukkit.getOnlinePlayers()) {
                    a.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(timerPrefix + Challenges.getInstance().getConfig().getString("timer.messages.timer-paused").replace("<time>", ConvertTimerTime(getCurrentTime()))));
                }
            }

            //reversed seconds = 0
            if(getTimerState() == TimerState.RUNNING && reversed) {
                if(getCurrentTime() <= 0) {
                    for(Player a : Bukkit.getOnlinePlayers()) {
                        a.setGameMode(GameMode.SPECTATOR);
                        a.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-reverse-expire"));
                    }
                    setTimerState(TimerState.PAUSED);
                }
            }
            //counting down or up
            if(getTimerState() == TimerState.RUNNING) {
                if(reversed) {
                    setCurrentTime(getCurrentTime() - 1);
                } else {
                    setCurrentTime(getCurrentTime() + 1);
                }
            }
        }, 0, 20);
    }

    public String ConvertTimerTime(int time) {
        int seconds = time % 60;
        int minutes = time / 60 % 60;
        int hours = time / 3600 % 24;
        int days = time / 86400 % 30;
        int months = time / 2592000 % 12;
        int years = time / 31104000;

        StringBuilder timerTime = new StringBuilder();
        if (years != 0) {
            if (years != 1) {
                timerTime.append(years).append(" Jahre, ");
            } else {
                timerTime.append(years).append(" Jahr, ");
            }
        }
        if (months != 0) {
            if (months != 1) {
                timerTime.append(months).append(" Monate, ");
            } else {
                timerTime.append(months).append(" Monat, ");
            }
        }
        if (days != 0) {
            if (days != 1) {
                timerTime.append(days).append(" Tage, ");
            } else {
                timerTime.append(days).append(" Tag, ");
            }
        }
        if (hours != 0) {
            timerTime.append(String.format("%02d", hours)).append(":");
        }
        timerTime.append(String.format("%02d", minutes)).append(":");
        timerTime.append(String.format("%02d", seconds));

        return timerTime.toString();
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public TimerState getTimerState() {
        return timerState;
    }

    public void setTimerState(TimerState timerState) {
        this.timerState = timerState;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public TimerGUI getTimerGUI() {
        return timerGUI;
    }

}
