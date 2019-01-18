package me.legenddev.legendraces.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Claim;
import me.legenddev.legendraces.manager.Race;
import me.legenddev.legendraces.manager.TerritoryManager;

public class RaceTerritory implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only.");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.raceterritory")) {
			p.sendMessage(Strings.noperms);
			return true;
		}
		if (args.length == 0) {
			List<String> msgs = Strings.file.getStringList("Help.Messages");
			for (String s : msgs) {
				String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg);
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("wand")) {
			p.getInventory()
					.addItem(TerritoryManager.wand("&aTerritory Manager".replace("&", "§"), Material.BLAZE_ROD, 0,
							Arrays.asList("&7Right Click for Corner 1".replace("&", "§"), " ",
									"&7Left Click for Corner 2".replace("&", "§"), " ",
									"&7Do /raceterritory add {race}".replace("&", "§"),
									"&7to add the territory".replace("&", "§")),
							1));
			p.updateInventory();
			if (!Main.getInstance().claiming.containsKey(p.getUniqueId().toString())) {
				Main.getInstance().claiming.put(p.getUniqueId().toString(), p.getLocation());
			}
			if (!Main.getInstance().corners.containsKey(p.getUniqueId().toString())) {
				List<Location> corners = new ArrayList<>();
				corners.add(Main.getInstance().claiming.get(p.getUniqueId().toString()));
				corners.add(Main.getInstance().claiming.get(p.getUniqueId().toString()));
				Main.getInstance().corners.put(p.getUniqueId().toString(), corners);
			}
			return true;
		} else if (args.length == 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("create"))) {
			Race race = Main.getInstance().getManager().getRaceByName(args[1]);
			if (race == null) {
				p.sendMessage(Strings.file.getString("Messages.invalidrace").replace("%prefix%", Main.getPrefix())
						.replace("&", "§"));
				return true;
			}
			List<Location> corners = new ArrayList<>();
			if (Main.getInstance().corners.containsKey(p.getUniqueId().toString())) {
				corners = Main.getInstance().corners.get(p.getUniqueId().toString());
			}
			if (corners.isEmpty() || corners.size() != 2
					|| corners.get(0) == Main.getInstance().claiming.get(p.getUniqueId().toString())
					|| corners.get(1) == Main.getInstance().claiming.get(p.getUniqueId().toString())) {
				p.sendMessage(Strings.file.getString("Messages.invalidclaim").replace("%prefix%", Main.getPrefix())
						.replace("&", "§"));
				return true;
			} else {
				FileConfiguration config = Main.getInstance().getRaces();
				race.setCornerOne(corners.get(0));
				race.setCornerTwo(corners.get(1));
				race.setClaim(new Claim(race, corners.get(0), corners.get(1)));
				config.set("races." + race.getName() + ".cornerOne",
						Main.getInstance().getManager().getStringofLocation(corners.get(0)));
				config.set("races." + race.getName() + ".cornerTwo",
						Main.getInstance().getManager().getStringofLocation(corners.get(1)));
				Main.getInstance().saveRaces();
				p.sendMessage(Strings.file.getString("Messages.successfulclaim").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replace("%race%", args[1]));
				for (ItemStack item : p.getInventory().getContents()) {
					if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
						continue;
					}
					if (item.getItemMeta().getDisplayName().contains("Territory Manager")) {
						item.setType(Material.AIR);
					}
				}
			}
			if (Main.getInstance().claiming.containsKey(p.getUniqueId().toString())) {
				Main.getInstance().claiming.remove(p.getUniqueId().toString());
			}
			if (Main.getInstance().corners.containsKey(p.getUniqueId().toString())) {
				Main.getInstance().corners.remove(p.getUniqueId().toString());
			}
		}
		return true;
	}

}
