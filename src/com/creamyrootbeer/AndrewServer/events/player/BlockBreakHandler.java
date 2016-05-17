package com.creamyrootbeer.AndrewServer.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.creamyrootbeer.AndrewServer.items.ASItem;

public class BlockBreakHandler implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		try {
			String handHeldName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
			
			for (ASItem item : ASItem.values()) {
				if (item.gameName == handHeldName) {
					event.setCancelled(true);
				}
			}
		} catch (Exception e) {
			return;
		}

	}
	
}
