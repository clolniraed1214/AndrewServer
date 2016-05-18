package com.creamyrootbeer.AndrewServer.util;

public class Direction {
	
	private double yaw;
	private double pitch;
	
	public Direction(double yaw, double pitch) {
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public double getYaw() {
		return this.yaw;
	}
	
	public double getPitch() {
		return this.pitch;
	}
	
}
