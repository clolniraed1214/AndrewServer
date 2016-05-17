package com.creamyrootbeer.AndrewServer.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.creamyrootbeer.AndrewServer.items.ASItem;

/**
 * Created by Collin on 5/8/2016.
 */
public class GetAccelRod implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if ( !(commandSender instanceof Player) ) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return false;
        }

        Player player = (Player) commandSender;
        
        if ( !(player.hasPermission("andrewserver.accrod")) ) {
        	player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return false;
        }
        
        player.getInventory().addItem(ASItem.ACC_ROD.getItem());

        return true;
    }

}
