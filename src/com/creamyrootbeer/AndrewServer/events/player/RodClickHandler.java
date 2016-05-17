package com.creamyrootbeer.AndrewServer.events.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.commands.Clickable;
import com.creamyrootbeer.AndrewServer.items.ASItem;
import com.creamyrootbeer.AndrewServer.util.Reloadable;

/**
 * Created by Collin on 5/8/2016.
 */
public class RodClickHandler implements Listener, Reloadable {

	// private double speedMult;
	// private double speedDiv;

	private Clickable leftClickAction;
	private Clickable rightClickAction;
	
	private FileConfiguration config;

	private ServerPlugin pl;

	public RodClickHandler(ServerPlugin pl) {
		this.pl = pl;
		this.config = pl.getConfig();
		
		reload();
	}

	public void reload() {
		leftClickAction = Clickable.valueOf(pl.getConfig().getString("Rod_Left_Click"));
		rightClickAction = Clickable.valueOf(pl.getConfig().getString("Rod_Right_Click"));
	}

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		try {
			if (item.getType().equals(Material.STICK)) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().getDisplayName().equals(ASItem.ACC_ROD.name)) {
						
						if (!(player.hasPermission("andrewserver.accelerate"))) {
							player.sendMessage(ChatColor.RED + "You do not have permission to use this item!");
							return;
						}

						if ((event.getAction() == Action.RIGHT_CLICK_AIR)
								|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
							rightClickAction.runAction(event, config);
						} else if (((event.getAction() == Action.LEFT_CLICK_AIR)
								|| (event.getAction() == Action.LEFT_CLICK_BLOCK))) {
							leftClickAction.runAction(event, config);
						}
					}
				}
			}
		} catch (NullPointerException e) {
		}
	}

	public static double getNewSpeed(double speedMult, double speed) {
		double newSpeed = speedMult * speed;
		newSpeed = newSpeed + Math.pow(.5D, (speed - 2) / 1.5);

		return newSpeed;
	}
}