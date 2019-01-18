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

public class SetRace implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender.hasPermission("races.setrace"))) {
			sender.sendMessage(Strings.noperms);
			return true;
		}
		if (args.length == 0) {
			List<String> msgs2 = Strings.file.getStringList("Help.Messages");
			for (String s2 : msgs2) {
				String msg2 = s2.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg2);
			}
			return true;
		}
		if (args.length == 1) {
			List<String> msgs2 = Strings.file.getStringList("Help.Messages");
			for (String s2 : msgs2) {
				String msg2 = s2.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg2);
			}
			return true;
		}
		if (args.length == 2) {
			Player p = (Player) sender;
			Player t = Bukkit.getServer().getPlayer(args[0]);
			if (t == null) {
				p.sendMessage(Strings.file.getString("Messages.playeroffline").replace("%player%", args[0])
						.replace("&", "§").replace("%prefix%", Main.getPrefix()).replace("&", "§"));
				return true;
			}
			Race race = Main.getInstance().getManager().getRaceByName(args[1]);
			if (race == null) {
				p.sendMessage(Strings.file.getString("Messages.invalidrace").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replace("%race%", args[1]).replace("&", "§"));
				return true;
			}
			PlayerFile file = GetPlayerFile.getPlayerFile(t);
			file.set("race", race.getName());
			file.save();
			p.sendMessage(Strings.file.getString("Messages.setplayerrace").replace("%prefix%", Main.getPrefix())
					.replace("&", "§").replace("%race%", race.getName()).replace("&", "§")
					.replace("%player%", t.getName()));
			t.sendMessage(
					Strings.file.getString("Messages.raceset").replace("%prefix%", Main.getPrefix()).replace("&", "§")
							.replace("%race%", race.getName()).replace("&", "§").replace("%player%", p.getName()));
			return true;
		}
		return true;
	}

}