package me.legenddev.legendraces.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class WhatRace implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("races.whatrace"))) {
			sender.sendMessage(Strings.noperms);
			return false;
		}
		if (args.length == 0) {
			List<String> msgs = Strings.file.getStringList("Help.Messages");
			for (String s : msgs) {
				String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg);
			}
			return false;
		}
		if (args.length == 1) {
			Player p = (Player) sender;
			Player t = Bukkit.getServer().getPlayer(args[0]);
			if (t == null) {
				p.sendMessage(Strings.file.getString("Messages.playeroffline").replace("%player%", args[0])
						.replace("&", "§").replace("%prefix%", Main.getPrefix()).replace("&", "§"));
				return false;
			}
			PlayerFile file = GetPlayerFile.getPlayerFile(t);
			Race race = Main.getInstance().getManager().getRaceByName(file.getString("race"));
			if (race == null) {
				p.sendMessage(Strings.file.getString("Messages.noplayerrace"));
				return false;
			}
			p.sendMessage(Strings.file.getString("Messages.whatrace").replace("%player%", t.getName()).replace("&", "§")
					.replace("%prefix%", Main.getPrefix()).replace("%race%", race.getName()));
			return true;
		}
		return true;
	}

}
