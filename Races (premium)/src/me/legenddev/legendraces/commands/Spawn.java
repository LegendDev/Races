package me.legenddev.legendraces.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.gui.Strings;

public class Spawn implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("racespawn")) {
			if (!(sender instanceof Player)) {
				Player t = Bukkit.getPlayer(args[1]);
				if (Main.getPlugin().getConfig().getConfigurationSection("spawn." + args[0].toLowerCase()) == null) {
					sender.sendMessage(
							Strings.file.getString("Messages.invalidspawn").replace("%spawn%", args[0].toLowerCase())
									.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
					return true;
				}
				World w = Bukkit.getServer()
						.getWorld(Main.getPlugin().getConfig().getString("spawn." + args[0].toLowerCase() + ".world"));
				double x = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".x");
				double y = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".y");
				double z = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".z");
				float pitch = (float) Main.getPlugin().getConfig()
						.getDouble("spawn." + args[0].toLowerCase() + ".pitch");
				float yaw = (float) Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".yaw");
				Location loc = new Location(w, x, y, z, yaw, pitch);
				t.teleport(loc);
				sender.sendMessage("Success");
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("races.spawn.others")) {
					p.sendMessage(String.valueOf(Main.getPrefix()) + " /racespawn (race) {player}");
					return true;
				}
				if (p.hasPermission("races.spawn")) {
					p.sendMessage(String.valueOf(Main.getPrefix()) + " /racespawn (race)");
					return true;
				}
				p.sendMessage(Strings.noperms);
				return true;
			} else if (args.length == 1) {
				if (!p.hasPermission("races.spawn")) {
					p.sendMessage(Strings.noperms);
					return true;
				}
				if (Main.getPlugin().getConfig().getConfigurationSection("spawn." + args[0].toLowerCase()) == null) {
					p.sendMessage(
							Strings.file.getString("Messages.invalidspawn").replace("%spawn%", args[0].toLowerCase())
									.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
					return true;
				}
				World w = Bukkit.getServer()
						.getWorld(Main.getPlugin().getConfig().getString("spawn." + args[0].toLowerCase() + ".world"));
				double x = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".x");
				double y = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".y");
				double z = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".z");
				float pitch = (float) Main.getPlugin().getConfig()
						.getDouble("spawn." + args[0].toLowerCase() + ".pitch");
				float yaw = (float) Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".yaw");
				Location loc = new Location(w, x, y, z, yaw, pitch);
				p.teleport(loc);
				p.sendMessage(Strings.file.getString("Messages.teleporting").replace("%spawn%", args[0].toLowerCase())
						.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
				return true;
			} else if (args.length == 2) {
				if (!p.hasPermission("races.spawn.others")) {
					p.sendMessage(Strings.noperms);
					return true;
				}
				Player t2 = Bukkit.getPlayer(args[1]);
				if (t2.getPlayer() == null) {
					p.sendMessage(Strings.file.getString("Messages.playeroffline").replace("%prefix%", Main.getPrefix())
							.replace("&", "§"));
					return true;
				}
				if (Main.getPlugin().getConfig().getConfigurationSection("spawn." + args[0].toLowerCase()) == null) {
					p.sendMessage(
							Strings.file.getString("Messages.invalidspawn").replace("%spawn%", args[0].toLowerCase())
									.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
					return true;
				}
				World w2 = Bukkit.getServer()
						.getWorld(Main.getPlugin().getConfig().getString("spawn." + args[0].toLowerCase() + ".world"));
				double x2 = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".x");
				double y2 = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".y");
				double z2 = Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".z");
				float pitch2 = (float) Main.getPlugin().getConfig()
						.getDouble("spawn." + args[0].toLowerCase() + ".pitch");
				float yaw2 = (float) Main.getPlugin().getConfig().getDouble("spawn." + args[0].toLowerCase() + ".yaw");
				Location loc2 = new Location(w2, x2, y2, z2, yaw2, pitch2);
				t2.teleport(loc2);
				t2.sendMessage(Strings.file.getString("Messages.teleporting").replace("%spawn%", args[0].toLowerCase())
						.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
				p.sendMessage(Strings.file.getString("Messages.teleportingother")
						.replace("%spawn%", args[0].toLowerCase()).replace("%prefix%", Main.getPrefix())
						.replace("%player%", t2.getName()).replace("&", "§"));
				return true;
			}
		}
		Player p = (Player) sender;
		if (!cmd.getName().equalsIgnoreCase("setracespawn")) {
			if (cmd.getName().equalsIgnoreCase("delracespawn")) {
				if (!p.hasPermission("races.delspawn")) {
					p.sendMessage(Strings.noperms);
					return true;
				}
				if (args.length == 0) {
					p.sendMessage(Strings.file.getString("Messages.invalidargs").replace("&", "§").replace("%prefix%",
							Main.getPrefix()));
					return true;
				}
				if (Main.getPlugin().getConfig().getConfigurationSection("spawn." + args[0].toLowerCase()) == null) {
					p.sendMessage(
							Strings.file.getString("Messages.invalidspawn").replace("%spawn%", args[0].toLowerCase())
									.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
					return true;
				}
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".world", null);
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".x", null);
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".y", null);
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".z", null);
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".yaw", null);
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".pitch", null);
				Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase(), null);
				Main.getPlugin().saveConfig();
				p.sendMessage(Strings.file.getString("Messages.spawndeleted").replace("&", "§").replace("%prefix%",
						Main.getPrefix()));
			}
			return false;
		}
		if (!p.hasPermission("races.setspawn")) {
			p.sendMessage(Strings.noperms);
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(Strings.file.getString("InvalidArgs.setracespawn").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
			return true;
		}
		Location location = p.getLocation();
		World world = p.getWorld();
		double x2 = location.getX();
		double y2 = location.getY();
		double z2 = location.getZ();
		float yaw = location.getYaw();
		float pitch3 = location.getPitch();
		Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".world", world.getName());
		Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".x", x2);
		Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".y", y2);
		Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".z", z2);
		Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".yaw", yaw);
		Main.getPlugin().getConfig().set("spawn." + args[0].toLowerCase() + ".pitch", pitch3);
		Main.getPlugin().saveConfig();
		p.sendMessage(
				Strings.file.getString("Messages.spawnset").replace("&", "§").replace("%prefix%", Main.getPrefix()));
		return true;
	} 
}
