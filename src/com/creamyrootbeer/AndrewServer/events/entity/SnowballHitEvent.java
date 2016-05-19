package com.creamyrootbeer.AndrewServer.events.entity;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.entities.SnowType;

public class SnowballHitEvent implements Listener {
	
	FileConfiguration config;
	
	public SnowballHitEvent() {
		this.config = ServerPlugin.getPl().getConfig();
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		if (!(event.getDamager() instanceof Snowball)) return;
		
		Snowball snow = (Snowball) event.getDamager();
		if (snow.hasMetadata("type")) {
			SnowType type = (SnowType) snow.getMetadata("type").get(0).value();
			
			type.hitPlayer(event, config);
			snow.remove();
		}
		
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile proj = event.getEntity();
		if (proj instanceof Snowball) {
			Snowball snow = (Snowball) proj;
			
			if (snow.hasMetadata("type")) {
				SnowType type = (SnowType) snow.getMetadata("type").get(0).value();
				
				type.hitOther(event, config);
				snow.remove();
			}
			
		}
	}
	
}
