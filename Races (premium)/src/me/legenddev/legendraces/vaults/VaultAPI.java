package me.legenddev.legendraces.vaults;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class VaultAPI {

	public static Map<Race, Inventory> vaults;

	static {
		vaults = new HashMap<>();
	}

	public static void saveVaults() {
		for (Race race : Main.getInstance().getManager().getRaces()) {
			if (vaults.containsKey(race)) {
				Inventory inv = vaults.get(race);
				ItemStack[] contents = inv.getContents();
				File vaultFile = Main.getInstance().getVaultManager().getVaultFile(race);
				YamlConfiguration vault = YamlConfiguration.loadConfiguration(vaultFile);
				vault.set("contents", contents);
				try {
					vault.save(vaultFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadVault(Player p) {
		Race race = RaceAPI.getPlayerRace(p);
		if (!vaults.containsKey(race)) {
			int size = Strings.file.getInt("Vault.size");
			String title = ChatColor.translateAlternateColorCodes('&', Strings.file.getString("Vault.title"));
			File raceFile = Main.getInstance().getVaultManager().getVaultFile(race);
			YamlConfiguration vault = YamlConfiguration.loadConfiguration(raceFile);
			Inventory inv = Bukkit.createInventory(p, size, title);
			if (vault.contains("contents")) {
				List<ItemStack> contents = (ArrayList<ItemStack>) vault.getList("contents");
				if (contents.size() >= 1) {
					for (int j = 0; j < size; j++) {
						if (contents.get(j) != null) {
							inv.setItem(j, contents.get(j));
						} else {
							inv.setItem(j, new ItemStack(Material.AIR));
						}
					}
				}
				p.openInventory(inv);
			} else {
				p.openInventory(inv);
			}
			vaults.put(race, inv);
		} else {
			p.openInventory(vaults.get(race));
		}
	}

}
