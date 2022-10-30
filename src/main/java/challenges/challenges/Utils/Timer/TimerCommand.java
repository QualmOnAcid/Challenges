package challenges.challenges.Utils.Timer;

import challenges.challenges.Challenges;
import challenges.challenges.Enums.TimerState;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You have to be a Player to use this Command!");
            return false;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 5);
            p.openInventory(Challenges.getTimer().getTimerGUI().getTimerInventory());
        }
        else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("resume")) {
                Challenges.getTimer().setTimerState(TimerState.RUNNING);
                p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-resumed-cmd"));
            } else if(args[0].equalsIgnoreCase("pause")) {
                Challenges.getTimer().setTimerState(TimerState.PAUSED);
                p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-paused-cmd"));
            } else if(args[0].equalsIgnoreCase("reverse")) {
                Challenges.getTimer().setReversed(!Challenges.getTimer().isReversed());
                p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-reversed-cmd"));
            } else if(args[0].equalsIgnoreCase("reset")) {
                int timeRightNow = Challenges.getTimer().getCurrentTime();
                Challenges.getTimer().setCurrentTime(0);
                Challenges.getTimer().setTimerState(TimerState.PAUSED);
                p.sendMessage("§e§lChallenges §7§l» §fDu hast den Timer zurückgesetzt");
                p.sendMessage("§e§lChallenges §7§l» §fZeit vorher: " + Challenges.getTimer().ConvertTimerTime(timeRightNow));
            }
        } else {
            p.sendMessage(Challenges.getInstance().getConfig().getString("timer.messages.timer-syntax"));
        }
        return false;
    }
}
