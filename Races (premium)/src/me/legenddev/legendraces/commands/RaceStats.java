package me.legenddev.legendraces.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class RaceStats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only.");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.racestats")) {
			p.sendMessage(Strings.noperms);
			return true;
		}
		if (args.length == 0) {
			Race race = RaceAPI.getPlayerRace(p);
			if (race != null) {
				sendRaceStats(race, p);
			} else {
				List<String> msgs = Strings.file.getStringList("Help.Messages");
				for (String s : msgs) {
					String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
					sender.sendMessage(msg);
				}
			}
		} else if (args.length == 1) {
			String name = args[0];
			if (Main.getInstance().getManager().getRaceByName(name) != null) {
				Race race = Main.getInstance().getManager().getRaceByName(name);
				sendRaceStats(race, p);
			} else {
				Player t = Bukkit.getPlayer(args[0]);
				if (t == null) {
					p.sendMessage(Strings.file.getString("Messaged.playeroffline").replace("&", "§"));
				} else {
					Race race = RaceAPI.getPlayerRace(p);
					if (race != null) {
						sendRaceStats(race, p);
					} else {
						p.sendMessage(Strings.file.getString("Messaged.noraceavailable").replace("&", "§"));
					}
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
	
	public void sendRaceStats(Race race, Player p) {
		for (String s : Strings.file.getStringList("Racestats.statsmessage")) {
			s = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
			s = s.replaceAll("%name%", race.getName());
			s = s.replaceAll("%tag%", race.getTag());
			s = s.replaceAll("%kills%", race.getKills() + "");
			s = s.replaceAll("%deaths%", race.getDeaths() + "");
			s = s.replaceAll("%balance%", race.getBalance() + "");
			s = s.replaceAll("%oresMined%", race.getOresMined() + "");
			s = s.replaceAll("%mobKills%", race.getMobKills() + "");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}
	
	

}
