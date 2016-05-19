package com.creamyrootbeer.AndrewServer.util.runnable;

import com.creamyrootbeer.AndrewServer.ServerPlugin;

public class DropNewBomb implements Runnable {

	private String playerName;
	
	public DropNewBomb(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public void run() {
		ServerPlugin.getVars().bombDelayNames.remove(playerName);

	}

}
