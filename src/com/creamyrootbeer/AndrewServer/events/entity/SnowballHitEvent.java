package com.creamyrootbeer.AndrewServer.events.entity;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class SnowballHitEvent implements Listener {
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (!(event.getDamager() instanceof Snowball)) return;
		
		Player player = (Player) event.getEntity();
		player.setVelocity(new Vector(0, -5D, 0));
		
	}
	
}
