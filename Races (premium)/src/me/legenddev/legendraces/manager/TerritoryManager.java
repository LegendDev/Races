package me.legenddev.legendraces.manager;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TerritoryManager {
	
	public static ItemStack wand(String name, Material material, int durability, List<String> lore, int amount) {
		ItemStack is = new ItemStack(material);
		if (durability > 0) {
			is.setDurability((short) durability);
		}
		if (amount > 1) {
			is.setAmount(amount);
		}
		if (is.getItemMeta() != null) {
			ItemMeta im = is.getItemMeta();
			if (name != null) {
				im.setDisplayName(name);
			}
			if (lore != null) {
				im.setLore(lore);
			}
			is.setItemMeta(im);
		}
		return is;
	}

}
