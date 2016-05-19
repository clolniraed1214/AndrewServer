package com.creamyrootbeer.AndrewServer.util.runnable;

import com.creamyrootbeer.AndrewServer.ServerPlugin;

public class PlayerAccelRemover implements Runnable {

	private String playerName;
	
	public PlayerAccelRemover(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public void run() {
		ServerPlugin.getVars().playerAccelList.remove(playerName);
	}
	
}
