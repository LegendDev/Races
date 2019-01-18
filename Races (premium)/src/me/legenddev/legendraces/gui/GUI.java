package me.legenddev.legendraces.gui;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.manager.Race;

@SuppressWarnings("unused")
public class GUI implements Listener {
	private static void createButton(Material mat, int amount, Inventory inv, int data, String name, String lore) {
		ItemStack item = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		inv.addItem(new ItemStack[] { item });
	}

	public static void createButton(int i, Material mat, int amount, Inventory inv, int data, String name,
			String[] lore) {
		ItemStack item = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		inv.setItem(i, item);
	}

	public static void createButton(int i, Material mat, int amount, Inventory inv, int data, String name,
			List<String> lore) {
		ItemStack item = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(i, item);
	}

	private static void createButton(int i, Material mat, int amount, Inventory inv, int data, String name) {
		ItemStack item = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		inv.setItem(i, item);
	}

	@SafeVarargs
	private static void createButton(int i, Material mat, int amount, Inventory inv, int data, String name,
			SimpleEntry<Enchantment, Integer>... enchants) {
		ItemStack item = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		for (SimpleEntry<Enchantment, Integer> enchantments : enchants) {
			item.addUnsafeEnchantment(enchantments.getKey(), enchantments.getValue());
		}
		inv.setItem(i, item);
	}

	private static SimpleEntry<Enchantment, Integer> enchantment(Enchantment enchant, Integer i) {
		return new SimpleEntry<Enchantment, Integer>(enchant, i);
	}

	public static Inventory main(Player p) {
		Inventory inv = Bukkit.getServer().createInventory(null, Strings.file.getInt("Gui.size"),
				Strings.file.getString("Gui.title").replace("&", "§"));
		for (Race race : Main.getInstance().getManager().getRaces()) {
			List<String> lore = new ArrayList<>();
			for (String s : race.getDescription()) {
				lore.add(ChatColor.translateAlternateColorCodes('&', s.replace("%current%", String
						.valueOf(RaceAPI.getRaceAmount(race)))));
			}
			if (race.isEnabled()) {
				for (String s : Strings.file.getStringList("Gui.enabledLore")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', s));
				}
			} else {
				for (String s : Strings.file.getStringList("Gui.disabledLore")) {
					lore.add(ChatColor.translateAlternateColorCodes('&', s));
				}
			}
			createButton(race.getSlot() - 1, race.getMat(), 1, inv, race.getData(),
					ChatColor.translateAlternateColorCodes('&', race.getDisplayName()), lore);
		}
		return inv;
	}

	public static Inventory confirm(Player p) {
		Inventory inv = Bukkit.getServer().createInventory(null, Strings.file.getInt("ConfirmGui.size"),
				Strings.file.getString("ConfirmGui.title").replace("&", "§"));
		List<String> lore1 = new ArrayList<>();
		List<String> lore2 = new ArrayList<>();
		for (String s : Strings.file.getStringList("ConfirmGui.confirmItem.lore")) {
			lore1.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		for (String s : Strings.file.getStringList("ConfirmGui.denyItem.lore")) {
			lore2.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		createButton((int)Strings.file.getInt("ConfirmGui.confirmItem.slot") - 1,
				Material.valueOf(Strings.file.getString("ConfirmGui.confirmItem.material")), 1, inv,
				Strings.file.getInt("ConfirmGui.confirmItem.data"), ChatColor.translateAlternateColorCodes('&',
						Strings.file.getString("ConfirmGui.confirmItem.displayName")), lore1);
		createButton((int)Strings.file.getInt("ConfirmGui.denyItem.slot") - 1,
				Material.valueOf(Strings.file.getString("ConfirmGui.denyItem.material")), 1, inv,
				Strings.file.getInt("ConfirmGui.denyItem.data"), ChatColor.translateAlternateColorCodes('&',
						Strings.file.getString("ConfirmGui.denyItem.displayName")), lore2);
		return inv;
	}
}
