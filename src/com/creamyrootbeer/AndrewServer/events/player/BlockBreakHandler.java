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
			if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ASItem.ACC_ROD.name)) return;
			event.setCancelled(true);
		} catch (Exception e) {
			return;
		}

	}
	
}
