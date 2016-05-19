package com.creamyrootbeer.AndrewServer.util.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.items.ASItem;

import net.md_5.bungee.api.ChatColor;

public class ReloadBombBay implements Runnable {

	private String playerName;
	
	public ReloadBombBay(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public void run() {
		
		int capacity = ServerPlugin.getPl().getConfig().getInt("BombBay_Capacity");
		ServerPlugin.getVars().bombCounts.put(playerName, capacity);
		
		Bukkit.getPlayer(playerName).sendMessage(ChatColor.GREEN + "Ready to Bomb!");

		
		Player player = Bukkit.getPlayer(playerName);
		ItemStack item = player.getInventory().getItemInOffHand();
		
		if (item.hasItemMeta()) {
			if (item.getItemMeta().getDisplayName().equals(ASItem.BOMB_BAY.gameName)) {
				ItemMeta meta = item.getItemMeta();
				meta.getLore().set(1, "<<" + capacity + ">>");
				item.setItemMeta(meta);
				player.getPlayer().getInventory().setItemInOffHand(item);
			}
		}
		
	}

}
