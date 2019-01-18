package me.legenddev.legendraces.commands;

import java.io.File;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.GUI;
import me.legenddev.legendraces.gui.Strings;
import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("changerace")) {
			Player p = (Player) sender;
			if (p.hasPermission("races.changerace")) {
				p.openInventory(GUI.main(p));
				return true;
			}
			if (!p.hasPermission("races.changerace")) {
				PlayerFile file = GetPlayerFile.getPlayerFile(p);
				if (file.get("racechanges") == null && p.hasPermission("races.onetimechange")) {
					p.openInventory(GUI.confirm(p));
					return true;
				}
				if (file.get("racechanges") == null) {
					List<String> msgs = Strings.file.getStringList("Messages.notenough");
					for (String s : msgs) {
						String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%changes%",
								new StringBuilder().append(file.getInt("racechanges")).toString());
						p.sendMessage(msg1);
					}
					return true;
				}
				if (file.getInt("racechanges") < 1) {
					List<String> msgs = Strings.file.getStringList("Messages.notenough");
					for (String s : msgs) {
						String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%changes%",
								new StringBuilder().append(file.getInt("racechanges")).toString());
						p.sendMessage(msg1);
					}
					return true;
				}
				file.set("race", null);
				file.set("racechanges", Integer.valueOf(file.getInt("racechanges")) - 1);
				p.openInventory(GUI.main(p));
				p.sendMessage(String.valueOf(Main.getPrefix()) + "You have used a racechange!");
				p.sendMessage(String.valueOf(Main.getPrefix()) + "You now have x" + file.getInt("racechanges")
						+ " changes available");
				List<String> msgs = Strings.file.getStringList("Messages.usedchange");
				for (String s : msgs) {
					String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%changes%",
							new StringBuilder().append(file.getInt("racechanges")).toString());
					p.sendMessage(msg1);
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("race")) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage(Strings.file.getString("InvalidArgs.race").replace("%prefix%", Main.getPrefix())
						.replace("&", "§"));
				return true;
			}
			if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
				List<String> msgs2 = Strings.file.getStringList("Help.Messages");
				for (String s2 : msgs2) {
					String msg2 = s2.replace("%prefix%", Main.getPrefix()).replace("&", "§");
					p.sendMessage(msg2);
				}
				return true;
			} else	if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if (p.hasPermission("races.admin")) {
					Main.getInstance().getManager().reloadRaces();
					Main.getInstance().reloadConfig();
					Strings.file = YamlConfiguration
							.loadConfiguration(new File(Main.getPlugin().getDataFolder(), "messages.yml"));
					p.sendMessage(ChatColor.GREEN + "Successfully reloaded Races.");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
				p.sendMessage(ChatColor.GREEN + "User ID: " + ChatColor.RESET + Main.uid);
				p.sendMessage(ChatColor.GREEN + "Resource ID: " + ChatColor.RESET + Main.rid);
				p.sendMessage(ChatColor.GREEN + "Spigot Link " + ChatColor.RESET + "https://www.spigotmc.org/members/spigotUser." + Main.uid + "/");
				p.sendMessage(ChatColor.GREEN + "Download ID: " + ChatColor.RESET + Main.nonce);
			} else {
				List<String> msgs2 = Strings.file.getStringList("Help.Messages");
				for (String s2 : msgs2) {
					String msg2 = s2.replace("%prefix%", Main.getPrefix()).replace("&", "§");
					p.sendMessage(msg2);
				}
				return true;
			}
		}
		return false;
	}
}
