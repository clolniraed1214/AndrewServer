package com.creamyrootbeer.AndrewServer.util.runnable;

import com.creamyrootbeer.AndrewServer.ServerPlugin;

public class AllowNewBombDrop implements Runnable {

	private String playerName;
	
	public AllowNewBombDrop(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public void run() {
		ServerPlugin.getVars().bombDelayNames.remove(playerName);

	}

}
