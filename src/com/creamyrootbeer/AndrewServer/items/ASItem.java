package com.creamyrootbeer.AndrewServer.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.creamyrootbeer.AndrewServer.util.ItemFactory;

public enum ASItem {

	ACC_ROD(ChatColor.AQUA + "Rod of Acceleration", "AccRod", "andrewserver.items.accrod") {
        @Override
        public ItemStack getItem() {
        	return ItemFactory.create(Material.STICK, this.gameName);
        }
    }

	;

	public final String gameName;
	public final String permission;
	public final String dataName;

    public abstract ItemStack getItem();
    
	ASItem(String name, String dataName, String permission) {
		this.gameName = name;
		this.permission = permission;
		this.dataName = dataName;
	}

}
