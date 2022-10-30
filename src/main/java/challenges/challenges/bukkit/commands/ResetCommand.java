package challenges.challenges.bukkit.commands;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import challenges.challenges.iChallenges.Challenges.Challenge;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You have to be a player!");
            return false;
        }
        Challenges.getTimer().setTimerState(TimerState.PAUSED);
        Player p = (Player) sender;
        Challenges.getInstance().saveConfig();
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.kickPlayer("§e§lMap-Reset");
        }
        for(Challenge c : Challenges.getInstance().getChallengeManager().getChallengeList()) {
            c.reset();
        }
        Bukkit.getScheduler().runTaskLater(Challenges.getInstance(), new Runnable() {
            @Override
            public void run() {
                Challenges.getInstance().getConfig().set("reset.reset", Boolean.valueOf(true));
                Challenges.getTimer().setCurrentTime(0);
                Challenges.getTimer().setReversed(false);
                Challenges.getInstance().saveConfig();
                Bukkit.spigot().restart();
            }
        }, 20 * 3);
        return false;
    }
}
