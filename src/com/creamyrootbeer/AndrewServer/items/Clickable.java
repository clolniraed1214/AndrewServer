package com.creamyrootbeer.AndrewServer.items;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.entities.SnowType;
import com.creamyrootbeer.AndrewServer.util.Direction;
import com.creamyrootbeer.AndrewServer.util.VectorUtils;
import com.creamyrootbeer.AndrewServer.util.meta.Meta;
import com.creamyrootbeer.AndrewServer.util.runnable.AllowNewBombDrop;
import com.creamyrootbeer.AndrewServer.util.runnable.AllowPlayerAccel;
import com.creamyrootbeer.AndrewServer.util.runnable.ReloadBombBay;
import com.creamyrootbeer.AndrewServer.util.runnable.SnowballRemover;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Collin on 5/16/2016.
 */
public enum Clickable {

	ACCELERATE {
		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			Player player = event.getPlayer();

			if (ServerPlugin.getVars().playerAccelList.contains(player.getName()))
				return;

			double maxSpeed = getMaxSpeed(player, config);
			double rate = config.getDouble("AccRod.RateOfAccel");

			Vector origVel = player.getVelocity();
			Vector addVel = player.getLocation().getDirection().multiply(rate);

			Vector newVel = origVel.add(addVel);
			if (newVel.length() > maxSpeed) {
				Direction dir = VectorUtils.getYawPitch(newVel);
				double newSpeed = maxSpeed;
				newVel = VectorUtils.getVector(newSpeed, dir.getYaw(), dir.getPitch());
			}
			player.setVelocity(newVel);

			ServerPlugin.getVars().playerAccelList.add(player.getName());

			long delay = config.getLong("AccRod.Delay");
			Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new AllowPlayerAccel(player.getName()),
					delay * 10L);
		}

		private double getMaxSpeed(Player player, FileConfiguration config) {
			try {
				if (player.getInventory().getItemInOffHand().getItemMeta().getDisplayName()
						.equals(ASItem.BOMB_BAY.gameName)) {
					return config.getDouble("AccRod.BomberMaxSpeed");
				} else {
					return config.getDouble("AccRod.MaxSpeed");
				}
			} catch (NullPointerException e) {
				return config.getDouble("AccRod.MaxSpeed");
			}

		}
	},
	DECELERATE {
		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			double speedDivisor = config.getDouble("Speed_Divisor");
			Player player = event.getPlayer();

			Vector vec = player.getVelocity();
			vec.multiply(1 / speedDivisor);

			player.setVelocity(vec);
		}
	},
	SET_SPEED {
		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			double staticSpeed = config.getDouble("Static_Speed");
			Player player = event.getPlayer();
			Direction yawPitch = VectorUtils.getPlayerYawPitch(player.getLocation());

			Vector vec = VectorUtils.getVector(staticSpeed, yawPitch.getYaw(), yawPitch.getPitch());
			player.setVelocity(vec);
		}
	},
	FLAK {

		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			Player player = event.getPlayer();
			Direction dir = VectorUtils.getPlayerYawPitch(player.getLocation());

			int radius = config.getInt("FlakSnowball.Radius");
			double interval = config.getDouble("FlakSnowball.Interval");
			double num = radius * interval;

			final ArrayList<Snowball> snowballs = new ArrayList<>();

			for (double x = -1 * num; x <= num; x += interval) {
				for (double y = -1 * num; y <= num; y += interval) {
					Vector snowVec = VectorUtils.getVector(5D, dir.getYaw() + x, dir.getPitch() + y);
					Location snowLoc = player.getLocation().add(0D, 1.8D, 0D);

					Snowball snow = (Snowball) Bukkit.getWorlds().get(0).spawnEntity(snowLoc, EntityType.SNOWBALL);
					snow.setVelocity(snowVec);
					snow.setMetadata("type", Meta.meta(SnowType.FLAK));

					snowballs.add(snow);
				}
			}

			Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new SnowballRemover(snowballs), 1 * 20L);

		}

	},
	BOMB {
		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {

			ClickableVars vars = ServerPlugin.getVars();
			String name = event.getPlayer().getName();

			if (!vars.bombCounts.containsKey(name))
				vars.bombCounts.put(name, config.getInt("BombBay.Capacity"));
			int bombs = vars.bombCounts.get(name);

			if (vars.bombCounts.get(name) == 0)
				return;
			if (vars.bombDelayNames.contains(name))
				return;

			Location loc = event.getPlayer().getLocation();
			loc.add(0, -0.5, 0);

			Vector vec = event.getPlayer().getVelocity();
			vec.setY(vec.getY() - .05);

			Snowball snow = (Snowball) loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
			snow.setMetadata("type", Meta.meta(SnowType.BOMB));
			snow.setVelocity(vec);

			vars.bombCounts.put(name, bombs - 1);

			Long reloadTime = config.getLong("BombBay.ReloadTime");
			Long delayTime = config.getLong("BombBay.BombDropDelay");

			if (bombs != 1)
				vars.bombDelayNames.add(name);

			if (bombs == 1) {
				Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new ReloadBombBay(name), reloadTime * 10L);
				event.getPlayer().sendMessage(ChatColor.GREEN + "Reloading Bombs!");
			} else {
				Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new AllowNewBombDrop(name), delayTime);
			}

			ItemStack item = event.getPlayer().getInventory().getItemInOffHand();

			if (item.hasItemMeta()) {
				if (item.getItemMeta().getDisplayName().equals(ASItem.BOMB_BAY.gameName)) {
					ItemMeta meta = item.getItemMeta();
					meta.getLore().set(1, "<<" + (bombs - 1) + ">>");
					item.setItemMeta(meta);
					
					PlayerInventory inv = event.getPlayer().getInventory();
					inv.setItemInOffHand(item);
				}
			}

		}
	},
	FIRE_GUN {

		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			Location playerLoc = event.getPlayer().getLocation().add(0D, 1.5D, 0D);
			double SPEED = 5D;
			Direction dir = VectorUtils.getPlayerYawPitch(playerLoc);

			Vector vec = VectorUtils.getVector(SPEED, dir);
			Snowball snow = (Snowball) playerLoc.getWorld().spawnEntity(playerLoc, EntityType.SNOWBALL);
			snow.setVelocity(vec);
			
			snow.setMetadata("type", Meta.meta(SnowType.BULLET));
			
			ArrayList<Snowball> snows = new ArrayList<Snowball>();
			snows.add(snow);
			Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new SnowballRemover(snows), 10L);
		}

	},
	NONE {
		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			return; // Does NOTHING
		}
	}

	;

	public abstract void runAction(PlayerInteractEvent event, FileConfiguration config);
}
