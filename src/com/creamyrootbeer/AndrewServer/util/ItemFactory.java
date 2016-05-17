package com.creamyrootbeer.AndrewServer.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {
	public static ItemStack create(Material material, String displayName, String[] lore, HashMap<Enchantment, Integer> enchants) {
		ItemStack item = new ItemStack(material);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(getLore(lore));
		
		item.setItemMeta(meta);
		item.addUnsafeEnchantments(enchants);
		
		return item;
	}
	
	public static ItemStack create(Material material, String displayName, String[] lore) {
		return create(material, displayName, lore, new HashMap<Enchantment, Integer>());
	}
	
	public static ItemStack create(Material material, String displayName) {
		return create(material, displayName, new String[]{});
	}
	
	public static ItemStack create(Material material, String displayName, HashMap<Enchantment, Integer> enchants) {
		return create(material, displayName, new String[]{}, enchants);
	}
	
	private static ArrayList<String> getLore(String[] lore) {
		ArrayList<String> newLore = new ArrayList<String>();
		for (String str : lore) {
			newLore.add(str);
		}
		
		return newLore;
	}
}
