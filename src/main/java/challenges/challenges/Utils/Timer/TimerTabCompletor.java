package challenges.challenges.Utils.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TimerTabCompletor implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("resume");
            list.add("pause");
            list.add("reverse");
            list.add("reset");
            return list;
        }
        return null;
    }
}
