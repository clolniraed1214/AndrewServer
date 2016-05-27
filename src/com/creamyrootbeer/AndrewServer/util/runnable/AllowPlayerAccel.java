package com.creamyrootbeer.AndrewServer.util.runnable;

import com.creamyrootbeer.AndrewServer.ServerPlugin;

public class AllowPlayerAccel implements Runnable {

	private String playerName;
	
	public AllowPlayerAccel(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public void run() {
		ServerPlugin.getVars().playerAccelList.remove(playerName);
	}
	
}
