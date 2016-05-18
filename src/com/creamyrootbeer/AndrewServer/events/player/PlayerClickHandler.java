package com.creamyrootbeer.AndrewServer.events.player;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.items.Clickable;
import com.creamyrootbeer.AndrewServer.items.ASItem;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Collin on 5/8/2016.
 */
public class PlayerClickHandler implements Listener {

	private FileConfiguration config;

	public PlayerClickHandler(ServerPlugin pl) {		
		this.config = pl.getConfig();
	}

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack handHeld = event.getItem();
		
		if (!handHeld.hasItemMeta())
			return;

		for (ASItem item : ASItem.values()) {
			if (handHeld.getItemMeta().getDisplayName().equals(item.gameName)) {
				
				if (!player.hasPermission("andrewserver.items.get." + item.dataName)) {
					player.sendMessage(ChatColor.RED + "You do not have the proper permissions to use this item!");
					return;
				}
				
				if ((event.getAction() == Action.RIGHT_CLICK_AIR)
						|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
					
					Clickable action = Clickable.valueOf(config.getString(item.dataName + "_Right_Click"));
					action.runAction(event, config);
					
				} else if (((event.getAction() == Action.LEFT_CLICK_AIR)
						|| (event.getAction() == Action.LEFT_CLICK_BLOCK))) {
					
					Clickable action = Clickable.valueOf(config.getString(item.dataName + "_Left_Click"));
					action.runAction(event, config);
					
				}
			}
		}
	}

	public static double getNewSpeed(double speedMult, double speed) {
		double newSpeed = speedMult * speed;
		newSpeed = newSpeed + Math.pow(.5D, (speed - 2) / 1.5);

		return newSpeed;
	}
}
