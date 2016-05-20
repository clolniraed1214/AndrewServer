package com.creamyrootbeer.AndrewServer.entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public enum SnowType {
	
	BOMB {
		@Override
		public void hitPlayer(EntityDamageByEntityEvent event, FileConfiguration config) {
			return; // Technically does something, but covered in hitOther()
		}

		@Override
		public void hitOther(ProjectileHitEvent event, FileConfiguration config) {
			Location boom = event.getEntity().getLocation();
			
			Float expSize = (float) config.getDouble("BombBay.ExplodeSize");
			boom.getWorld().createExplosion(boom, expSize);
			
		}
	},
	FLAK {
		@Override
		public void hitPlayer(EntityDamageByEntityEvent event, FileConfiguration config) {
			Player player = (Player) event.getEntity();
			player.setVelocity(new Vector(0, -10D, 0));
			
			ItemStack chestplate = player.getInventory().getChestplate();
			if (chestplate.getType().equals(Material.ELYTRA)) {
				short damage = chestplate.getDurability();
				damage += config.getInt("FlakSnowball.ElytraDamage");
				
				if (damage > 432) {
					damage = 432;
				}
				
				chestplate.setDurability(damage);
				
				player.getInventory().setChestplate(chestplate);
			}
		}

		@Override
		public void hitOther(ProjectileHitEvent event, FileConfiguration config) {
			// TODO Auto-generated method stub
			
		}
	},
	BULLET {

		@Override
		public void hitPlayer(EntityDamageByEntityEvent event, FileConfiguration config) {
			Player player = (Player) event.getEntity();
			player.damage(config.getDouble("AircraftGun.Damage"));
			
		}

		@Override
		public void hitOther(ProjectileHitEvent event, FileConfiguration config) {
			return;
			
		}
		
	}
	;
	
	public abstract void hitPlayer(EntityDamageByEntityEvent event, FileConfiguration config);
	public abstract void hitOther(ProjectileHitEvent event, FileConfiguration config);
	
}
