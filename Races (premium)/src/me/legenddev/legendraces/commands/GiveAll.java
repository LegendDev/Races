package me.legenddev.legendraces.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class GiveAll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only.");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.giveall")) {
			p.sendMessage(Strings.noperms);
		}
		if (args.length >= 3) {
			List<Player> players = new ArrayList<>();
			if (args[0].equalsIgnoreCase("all")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					players.add(player);
				}
			} else {
				Race race = RaceAPI.getRaceByString(args[0]);
				if (race == null) {
					p.sendMessage(Strings.file.getString("Messages.invalidrace").replace("%prefix%", Main.getPrefix())
							.replace("&", "§"));
					return true;
				}
				for (Player player : Bukkit.getOnlinePlayers()) {
					Race pRace = RaceAPI.getPlayerRace(player);
					if (pRace == race) {

					}
				}
			}
			if (args[1].equalsIgnoreCase("money")) {
				int price = 0;
				try {
					price = Integer.valueOf(args[2]);
					if (price <= 0) {
						List<String> msgs = Strings.file.getStringList("Help.Messages");
						for (String s : msgs) {
							String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
							sender.sendMessage(msg);
						}
						return true;
					}
				} catch (Exception ex) {
					List<String> msgs = Strings.file.getStringList("Help.Messages");
					for (String s : msgs) {
						String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
						sender.sendMessage(msg);
					}
					return true;
				}
				for (Player ply : players) {
					Main.getInstance().getEconomy().depositPlayer(ply, price);
					ply.sendMessage(Strings.file.getString("Messages.prizereceived")
							.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
				}
				p.sendMessage(Strings.file.getString("Messages.prizegiven").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replace("%race%", args[0]).replace("%type%", "Money")
						.replace("%data%", args[2]));
			} else if (args[1].equalsIgnoreCase("command")) {
				StringBuilder string = new StringBuilder();
				for (int i = 2; i < args.length; i++) {
					if (i > 2) {
						string.append(" ");
					}
					string.append(args[i]);
				}
				Bukkit.getConsoleSender().sendMessage(string.toString());
				for (Player ply : players) {
					Race race = RaceAPI.getPlayerRace(ply);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							string.toString().replace("%player%", ply.getName()).replaceAll("%race%", race.getName()));
					ply.sendMessage(Strings.file.getString("Messages.prizereceived")
							.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
				}
				p.sendMessage(Strings.file.getString("Messages.prizegiven").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replace("%race%", args[0]).replace("%type%", "Command")
						.replace("%data%", string.toString()));
			} else {
				List<String> msgs = Strings.file.getStringList("Help.Messages");
				for (String s : msgs) {
					String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
					sender.sendMessage(msg);
				}
			}
		} else {
			List<String> msgs = Strings.file.getStringList("Help.Messages");
			for (String s : msgs) {
				String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg);
			}
		}
		return true;
	}

	public String allArgs(int start, String[] args) {
		String temp = "";
		for (int i = start; i < args.length; i++) {
			temp += args[i] + " ";
		}
		return temp.trim();
	}

}
