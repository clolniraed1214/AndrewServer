package com.creamyrootbeer.AndrewServer.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.creamyrootbeer.AndrewServer.events.player.PlayerClickHandler;
import com.creamyrootbeer.AndrewServer.util.VectorUtils;

/**
 * Created by Collin on 5/16/2016.
 */
public enum Clickable {

    ACCELERATE {
        @Override
        public void runAction(PlayerInteractEvent event, FileConfiguration config) {
            double speedMultiplier = config.getDouble("Speed_Multiplier");
            Player player = event.getPlayer();
            double[] yawPitch = VectorUtils.getPlayerYawPitch(player.getLocation());
            double speed = PlayerClickHandler.getNewSpeed(player.getVelocity().length(), speedMultiplier);

            Vector vec = VectorUtils.getVector(speed, yawPitch[0], yawPitch[1]);
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
            double[] yawPitch = VectorUtils.getPlayerYawPitch(player.getLocation());

            Vector vec = VectorUtils.getVector(staticSpeed, yawPitch[0], yawPitch[1]);
            player.setVelocity(vec);
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

    /*protected double speedMultiplier = Bukkit.getPluginManager().getPlugin("AndrewServer").getConfig().getDouble("Speed_Multiplier");
    protected double speedDivisor = Bukkit.getPluginManager().getPlugin("AndrewServer").getConfig().getDouble("Speed_Divisor");
    protected double staticSpeed = Bukkit.getPluginManager().getPlugin("AndrewServer").getConfig().getDouble("Static_Speed");

    public void reload() {
        speedMultiplier = Bukkit.getPluginManager().getPlugin("AndrewServer").getConfig().getDouble("Speed_Multiplier");
        speedDivisor = Bukkit.getPluginManager().getPlugin("AndrewServer").getConfig().getDouble("Speed_Divisor");
        staticSpeed = Bukkit.getPluginManager().getPlugin("AndrewServer").getConfig().getDouble("Static_Speed");
    }*/
}
