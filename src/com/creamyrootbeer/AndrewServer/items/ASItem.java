package com.creamyrootbeer.AndrewServer.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.creamyrootbeer.AndrewServer.util.EasyItem;

public enum ASItem {

	ACC_ROD(ChatColor.AQUA + "Rod of Acceleration", "AccRod", false) {
        @Override
        public ItemStack getItem() {
        	return EasyItem.Create(Material.STICK, this.gameName);
        }
    },
	FLAK_CANNON(ChatColor.RED + "Flak Cannon", "FlakSnowball", false) {

		@Override
		public ItemStack getItem() {
			return EasyItem.Create(Material.SNOW_BALL, gameName);
		}
		
	},
	BOMB_BAY(ChatColor.RED + "Bombs", "BombBay", false) {

		@Override
		public ItemStack getItem() {
			return EasyItem.Create(Material.APPLE, gameName, new String[]{"<<5>>", "Bombs Left"});
		}
		
	},
	AIRCRAFT_GUN(ChatColor.RED + "Gun", "AircraftGun", false) {

		@Override
		public ItemStack getItem() {
			return EasyItem.Create(Material.SNOW_BALL, gameName);
		}
		
	}
	;

	public final String gameName;
	public final String dataName;
	public final boolean droppable;
	
    public abstract ItemStack getItem();
    
	ASItem(String name, String dataName, boolean droppable) {
		this.gameName = name;
		this.dataName = dataName;
		this.droppable = droppable;
	}

}
