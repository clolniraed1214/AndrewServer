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
public class GetCustomItem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if ( !(commandSender instanceof Player) ) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return false;
        }

        Player player = (Player) commandSender;
        
        if (args.length != 2) {
        	player.sendMessage(ChatColor.RED + "Proper usage is /getitem <itemname> <amount>");
        	return false;
        }
        
        ASItem item = ASItem.ACC_ROD;
        try {
        	item = ASItem.valueOf(args[0]);
        } catch (IllegalArgumentException e) {
        	player.sendMessage(ChatColor.RED + "That is not a valid item!");
        	return false;
        }
        
        if ( !(player.hasPermission("andrewserver.item.get." + item.dataName)) ) {
        	player.sendMessage(ChatColor.RED + "You don't have permission to get this item!");
            return false;
        }

        int count = 1;
        try {
        	count = Integer.parseInt(args[1]);
        } catch (Exception e) {
        	player.sendMessage(ChatColor.RED + "That is not a valid number!");
        	return false;
        }
        
        player.getInventory().setItemInOffHand(item.getItem());
        player.getInventory().getItemInOffHand().setAmount(count);

        return true;
    }

}
