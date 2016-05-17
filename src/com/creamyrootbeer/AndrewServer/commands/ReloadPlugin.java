package com.creamyrootbeer.AndrewServer.commands;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Collin on 5/16/2016.
 */
public class ReloadPlugin implements CommandExecutor {

    private ServerPlugin pl;

    public ReloadPlugin(ServerPlugin pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length != 1) {
            commandSender.sendMessage(ChatColor.RED + "Incorrect Format!");
        }

        if (strings[0].equals("reload")) {
            pl.reloadConfigs();
        }

        return true;
    }
}
