package com.creamyrootbeer.AndrewServer.util;

import java.util.ArrayList;

import org.bukkit.entity.Snowball;

public interface SnowballRemover extends Runnable {

	@Override
	public void run();
	public void snowballs(ArrayList<Snowball> snowballs);

}
