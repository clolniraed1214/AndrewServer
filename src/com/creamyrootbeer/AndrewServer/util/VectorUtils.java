package com.creamyrootbeer.AndrewServer.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class VectorUtils {
    
    public static Vector getVector(double speed, double yaw, double pitch) {
    	World world = Bukkit.getWorlds().get(0);
    	Location loc = new Location(world, 0D, 0D, 0D, (float) yaw, (float) pitch);
    	
    	Vector vec = loc.getDirection();
    	vec.multiply(speed);
    	
    	return vec;
	}
    
    public static double[] getPlayerYawPitch(Location loc) {
    	double[] yawPitch = new double[2];

    	yawPitch[0] = loc.getYaw();
    	yawPitch[1] = loc.getPitch();
    	
    	return yawPitch;
    }
    
    public static double[] getYawPitch(Vector vec) {
    	
    	World world = Bukkit.getWorlds().get(0);
    	Location loc = new Location(world, 0D, 0D, 0D);
    	loc.setDirection(vec);
    	
    	double[] yawPitch = new double[2];
    	yawPitch[0] = loc.getYaw();
    	yawPitch[1] = loc.getPitch();
    	
    	return yawPitch;
    	
    }
    
    /*
    private static double pythagHyp(double val1, double val2) {
    	return Math.sqrt(Math.pow(val1, 2D) + Math.pow(val2, 2D));
    }
    
    private static int getNumSign(double num) {
    	int sign = (int) (Math.abs(num)/num); // Gets the sign of the integer, positive or negative. Returned as 1 or -1
    	
    	return sign;
    }
    
    private static double fixSin(double angle, double item) {
    	if (item < 0) {
    		if (angle < 0) {
    			return Math.toRadians(-180) - angle;
    		} else {
    			return Math.toRadians(180) - angle;
    		}
    	} else {
    		return angle;
    	}
    }
    */
}
