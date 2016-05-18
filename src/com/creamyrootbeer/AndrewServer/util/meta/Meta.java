package com.creamyrootbeer.AndrewServer.util.meta;

import org.bukkit.metadata.FixedMetadataValue;

import com.creamyrootbeer.AndrewServer.ServerPlugin;

public class Meta {
	
	public static FixedMetadataValue meta(Object object) {
		return new FixedMetadataValue(ServerPlugin.getPl(), object);
	}
	
}
