package me.legenddev.legendraces.leaderboards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class TopGUI {

	public static void createButton(int i, Material mat, int amount, Inventory inv, int data, String name,
			List<String> lore) {
		ItemStack item = new ItemStack(mat, amount, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(i, item);
	}

	public TopGUI(Player p, List<Race> races) {
		Inventory inv = Bukkit.getServer().createInventory(null, Strings.file.getInt("TopRaces.gui.size"),
				Strings.file.getString("TopRaces.gui.title").replace("&", "§"));
		int i = 0;
		for (Race race : races) {
			List<String> lore = new ArrayList<>();
			for (String s : Strings.file.getStringList("TopRaces.gui.raceLore")) {
				s = s.replace("{number}", (i + 1) + "");
				s = s.replace("{name}", race.getName());
				s = s.replace("{balance}", race.getBalance() + "");
				s = s.replace("{kills}", race.getKills() + "");
				s = s.replace("{deaths}", race.getDeaths() + "");
				s = s.replace("{oresMined}", race.getOresMined() + "");
				s = s.replace("{mobKills}", race.getMobKills() + "");
				lore.add(ChatColor.translateAlternateColorCodes('&', s));
			}
			String displayName = Strings.file.getString("TopRaces.gui.itemName").replace("&", "§").replace("{number}", (i + 1) + "")
					.replace("{displayName}", ChatColor.translateAlternateColorCodes('&', race.getDisplayName()));
			createButton(i, race.getMat(), 1, inv, race.getData(), displayName, lore);
			++i;
		}
		p.openInventory(inv);
	}

}