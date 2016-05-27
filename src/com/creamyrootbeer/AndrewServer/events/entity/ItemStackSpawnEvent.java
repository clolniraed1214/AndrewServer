package com.creamyrootbeer.AndrewServer.events.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import com.creamyrootbeer.AndrewServer.items.ASItem;

public class ItemStackSpawnEvent implements Listener {

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {
		try {
			for (ASItem item : ASItem.values()) {

				if (item.droppable) {
					if (event.getEntity().getItemStack().getItemMeta().getDisplayName().equals(item.gameName)) {
						event.setCancelled(true);
					}
				}

			}
		} catch (NullPointerException e) {

		}
	}

}
