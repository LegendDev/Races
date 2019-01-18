package me.legenddev.legendraces.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class ChannelsDynamic implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("channel")) {
			boolean DisableChannels = Main.getPlugin().getConfig().getBoolean("DisableChannels");
			if (DisableChannels) {
				p.sendMessage(Strings.file.getString("Messages.channelsdisabled").replace("%prefix%", Main.getPrefix())
						.replace("&", "§"));
				return true;
			}
			if (args.length == 0) {
				PlayerFile file = GetPlayerFile.getPlayerFile(p);
				if (file.getString("channel") == null) {
					file.set("channel", file.getString("race"));
					file.save();
					List<String> msgs = Strings.file.getStringList("Messages.dynamicswitchedchannels");
					for (String s : msgs) {
						String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%race%",
								file.getString("race"));
						p.sendMessage(msg1);
					}
					return true;
				}
				if (file.getString("channel").equalsIgnoreCase(file.getString("race"))) {
					file.set("channel", "global");
					file.save();
					List<String> msgs = Strings.file.getStringList("Messages.dynamicswitchedchannels");
					for (String s : msgs) {
						String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%race%",
								"Global");
						p.sendMessage(msg1);
					}
					return true;
				}
				file.set("channel", file.getString("race"));
				file.save();
				List<String> msgs = Strings.file.getStringList("Messages.dynamicswitchedchannels");
				for (String s : msgs) {
					String msg1 = s.replace("%prefix%", Main.getPrefix()).replace("&", "§").replace("%race%",
							file.getString("race"));
					p.sendMessage(msg1);
				}
			}
			String r = "";
			if (args.length > 0) {
				for (int i = 0; i < args.length; ++i) {
					r = String.valueOf(r) + args[i] + " ";
				}
				PlayerFile file2 = GetPlayerFile.getPlayerFile(p);
				for (Race race : Main.getInstance().getManager().getRaces()) {
					if (file2.getString("channel") == null) return false;
					if (file2.getString("channel").equalsIgnoreCase(race.getName())) {
						for (Player on : Bukkit.getOnlinePlayers()) {
							PlayerFile recievers = GetPlayerFile.getPlayerFile(on);
							if (recievers.getString("channel").equalsIgnoreCase(file2.getString("channel"))) {
								on.sendMessage(Strings.file.getString("Channels.format")
										.replace("%channel%", file2.getString("channel")).replace("%msg%", r).replace("&", "§")
										.replace("%player%", p.getName()));
							}
						}
					}
				}

			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerChannelChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PlayerFile file = GetPlayerFile.getPlayerFile(p);
		if (file.getString("channel") != null) {
			for (Race race : Main.getInstance().getManager().getRaces()) {
				if (file.getString("channel").equalsIgnoreCase(race.getName())) {
					for (Player on : Bukkit.getOnlinePlayers()) {
						PlayerFile recievers = GetPlayerFile.getPlayerFile(on);
						if (recievers.getString("channel").equalsIgnoreCase(file.getString("channel"))) {
							e.setCancelled(true);
							on.sendMessage(Strings.file.getString("Channels.format")
									.replace("%channel%", file.getString("channel")).replace("%msg%", e.getMessage())
									.replace("&", "§").replace("%player%", p.getName()));
						}
					} 
				}
			}
		}
	}
}
