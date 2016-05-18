package com.creamyrootbeer.AndrewServer.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EasyItem extends ItemStack {
	public EasyItem(Material material, String displayName, String[] lore, HashMap<Enchantment, Integer> enchants) {
		super(material);
		
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(displayName);
		meta.setLore(getLore(lore));
		
		this.setItemMeta(meta);
		this.addUnsafeEnchantments(enchants);
	}
	
	public EasyItem(Material material, String displayName, String[] lore) {
		this(material, displayName, lore, new HashMap<Enchantment, Integer>());
	}
	
	public EasyItem(Material material, String displayName) {
		this(material, displayName, new String[]{});
	}
	
	public EasyItem(Material material, String displayName, HashMap<Enchantment, Integer> enchants) {
		this(material, displayName, new String[]{}, enchants);
	}
	
	private static ArrayList<String> getLore(String[] lore) {
		ArrayList<String> newLore = new ArrayList<String>();
		for (String str : lore) {
			newLore.add(str);
		}
		
		return newLore;
	}
}
