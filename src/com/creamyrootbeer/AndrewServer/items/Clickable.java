package com.creamyrootbeer.AndrewServer.items;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import com.creamyrootbeer.AndrewServer.ServerPlugin;
import com.creamyrootbeer.AndrewServer.entities.SnowType;
import com.creamyrootbeer.AndrewServer.events.player.PlayerClickHandler;
import com.creamyrootbeer.AndrewServer.util.Direction;
import com.creamyrootbeer.AndrewServer.util.SnowballRemover;
import com.creamyrootbeer.AndrewServer.util.VectorUtils;
import com.creamyrootbeer.AndrewServer.util.meta.Meta;

/**
 * Created by Collin on 5/16/2016.
 */
public enum Clickable {

    ACCELERATE {
        @Override
        public void runAction(PlayerInteractEvent event, FileConfiguration config) {
            double speedMultiplier = config.getDouble("Speed_Multiplier");
            Player player = event.getPlayer();
            Direction yawPitch = VectorUtils.getPlayerYawPitch(player.getLocation());
            double speed = PlayerClickHandler.getNewSpeed(player.getVelocity().length(), speedMultiplier);

            Vector vec = VectorUtils.getVector(speed, yawPitch.getYaw(), yawPitch.getPitch());
            player.setVelocity(vec);
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
			
			SnowballRemover remover = new SnowballRemover(){
				private ArrayList<Snowball> snowballs;
				
				public void snowballs(ArrayList<Snowball> snowballs) {
					this.snowballs = snowballs;
				}
				
				@Override
				public void run() {
					for (Snowball snow : this.snowballs) {
						snow.remove();
					}
				}
				
			};
			
			remover.snowballs(snowballs);
			
			Bukkit.getScheduler().runTaskLater(ServerPlugin.getPl(), remover, 1 * 20L);
			
		}
    	
    },
    BOMB {
		@Override
		public void runAction(PlayerInteractEvent event, FileConfiguration config) {
			Location loc = event.getPlayer().getLocation();
			loc.add(0, -0.5, 0);
			
			Vector vec = event.getPlayer().getVelocity();
			vec.setY(vec.getY() - .05);
			
			Snowball snow = (Snowball) loc.getWorld().spawnEntity(loc, EntityType.SNOWBALL);
			snow.setMetadata("type", Meta.meta(SnowType.BOMB));
			snow.setVelocity(vec);
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
