package com.creamyrootbeer.AndrewServer.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ASItem {

	ACC_ROD(ChatColor.AQUA + "Rod of Acceleration") {
        @Override
        public ItemStack getItem() {

            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(this.name);
            item.setItemMeta(meta);

            return item;
        }
    }

	;

	public final String name;

    public abstract ItemStack getItem();

	ASItem(String name) {
		this.name = name;
	}

}
