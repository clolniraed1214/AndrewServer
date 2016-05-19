package com.creamyrootbeer.AndrewServer.util.runnable;

import java.util.ArrayList;

import org.bukkit.entity.Snowball;

public class SnowballRemover implements Runnable {

	private ArrayList<Snowball> snowballs;
	
	public SnowballRemover(ArrayList<Snowball> snowballs) {
		this.snowballs = snowballs;
	}
	
	@Override
	public void run() {
		for (Snowball snow : this.snowballs) {
			snow.remove();
		}
	}

}
