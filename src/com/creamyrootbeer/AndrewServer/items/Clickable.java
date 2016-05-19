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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.entities.SnowType;
import com.creamyrootbeer.AndrewServer.util.Direction;
import com.creamyrootbeer.AndrewServer.util.VectorUtils;
import com.creamyrootbeer.AndrewServer.util.meta.Meta;
import com.creamyrootbeer.AndrewServer.util.runnable.DropNewBomb;
import com.creamyrootbeer.AndrewServer.util.runnable.PlayerAccelRemover;
import com.creamyrootbeer.AndrewServer.util.runnable.ReloadBombBay;
import com.creamyrootbeer.AndrewServer.util.runnable.SnowballRemover;

/**
 * Created by Collin on 5/16/2016.
 */
public enum Clickable {

    ACCELERATE {
        @Override
        public void runAction(PlayerInteractEvent event, FileConfiguration config) {
        	Player player = event.getPlayer();
        	
        	if (ServerPlugin.getVars().playerAccelList.contains(player.getName())) return;
        	
        	double maxSpeed = config.getDouble("AccRod_MaxSpeed");
        	double rate = config.getDouble("AccRod_RateOfAccel");
        	
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
        	
        	long delay = config.getLong("AccRod_Delay");       	
        	Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new PlayerAccelRemover(player.getName()), delay*10L);
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
			
			int radius = 6;
			double interval = .3;
			double num = radius * interval;
			
			final ArrayList<Snowball> snowballs = new ArrayList<>(); 
			
			for (double x = -1 * num; x <= num; x += interval) {
				for (double y = -1 * num; y <= num; y += interval) {
					Vector snowVec = VectorUtils.getVector(6D, dir.getYaw()+x, dir.getPitch()+y);
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
			
			if (!vars.bombCounts.containsKey(name)) vars.bombCounts.put(name, config.getInt("BombBay_Capacity"));
			int bombs = vars.bombCounts.get(name);
			
			if (vars.bombCounts.get(name) == 0) return;
			if (vars.bombDelayNames.contains(name)) return;
			
			Location loc = event.getPlayer().getLocation();
			loc.add(0, -0.5, 0);
			
			Vector vec = event.getPlayer().getVelocity();
			vec.setY(vec.getY() - .05);
			
			Snowball snow = (Snowball) loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
			snow.setMetadata("type", Meta.meta(SnowType.BOMB));
			snow.setVelocity(vec);
			
			vars.bombCounts.put(name, bombs-1);
			
			Long reloadTime = config.getLong("BombBay_ReloadTime");
			Long delayTime = config.getLong("BombBay_BombDropDelay");
			
			if (bombs != 1) vars.bombDelayNames.add(name);
			
			if (bombs == 1) {Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new ReloadBombBay(name), reloadTime * 10L);}
			else {Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), new DropNewBomb(name), delayTime);}
			
			ItemStack item = event.getPlayer().getInventory().getItemInOffHand();
			
			if (item.hasItemMeta()) {
				if (item.getItemMeta().getDisplayName().equals(ASItem.BOMB_BAY.gameName)) {
					ItemMeta meta = item.getItemMeta();
					meta.getLore().set(1, "<<" + (bombs-1) + ">>");
					item.setItemMeta(meta);
					event.getPlayer().getInventory().setItemInOffHand(item);
				}
			}
			
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
