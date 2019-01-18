package me.legenddev.legendraces.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.Strings;

public class ChangeRace implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		if (!cmd.getName().equalsIgnoreCase("addracechanges")) {
			return false;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.GREEN + "Success.");
			Player t = Bukkit.getPlayer(args[0]);
			PlayerFile file = GetPlayerFile.getPlayerFile(t);
			file.set("racechanges",
					Integer.valueOf(file.getInt("racechanges")) + Integer.valueOf(Integer.parseInt(args[1])));
			file.save();
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.addracechanges")) {
			p.sendMessage(String.valueOf(Main.getPrefix()) + "No Permission.");
			return true;
		}
		if (args.length == 0 || args.length == 1) {
			p.sendMessage(Strings.file.getString("InvalidArgs.addracechanges").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
			return true;
		}
		Player t2 = Bukkit.getPlayer(args[0]);
		if (t2 == null) {
			p.sendMessage(Strings.file.getString("Messages.playeroffline").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
			return true;
		}
		PlayerFile file2 = GetPlayerFile.getPlayerFile(t2);
		file2.set("racechanges",
				Integer.valueOf(file2.getInt("racechanges")) + Integer.valueOf(Integer.parseInt(args[1])));
		file2.save();
		p.sendMessage(Strings.file.getString("Messages.updatedchanges").replace("%prefix%", Main.getPrefix())
				.replace("%player%", t2.getName()).replace("&", "§"));
		List<String> msgs = Strings.file.getStringList("Messages.updatedtootherplayer");
		for (String s : msgs) {
			String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%amount%", args[1])
					.replace("%changes%", new StringBuilder().append(file2.getInt("racechanges")).toString());
			t2.sendMessage(msg1);
		}
		return true;
	}
}
