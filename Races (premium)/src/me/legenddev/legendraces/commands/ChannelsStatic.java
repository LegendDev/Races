package me.legenddev.legendraces.commands;

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

public class ChannelsStatic implements CommandExecutor, Listener {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (!cmd.getName().equalsIgnoreCase("channel")) {
			return false;
		}
		boolean DisableChannels = Main.getPlugin().getConfig().getBoolean("disablechannels");
		if (DisableChannels) {
			p.sendMessage(Strings.file.getString("Messages.channelsdisabled").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
			return true;
		}
		if (args.length == 0) {
			p.sendMessage(Strings.file.getString("InvalidArgs.channelstatic").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
			return true;
		}
		PlayerFile file = GetPlayerFile.getPlayerFile(p);
		if (!args[0].equalsIgnoreCase("global") && !file.getString("race").equalsIgnoreCase(args[0])) {
			p.sendMessage(Strings.file.getString("Messages.noaccesstochannel").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
			return true;
		}
		p.sendMessage(Strings.file.getString("Messages.switchedchannels").replace("%prefix%", Main.getPrefix())
				.replace("%channel%", args[0].toLowerCase()).replace("&", "§"));
		file.set("channel", args[0].toLowerCase());
		file.save();
		return true;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PlayerFile file = GetPlayerFile.getPlayerFile(p);
		if (file.getString("channel") == null) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				PlayerFile file2 = GetPlayerFile.getPlayerFile(on);
				if (file2.getString("channel") == null) {
					e.setCancelled(false);
				}
			}
		}
		if (file.getString("channel") != null && file.getString("channel").equalsIgnoreCase("global")) {
			for (Player on : Bukkit.getOnlinePlayers()) {
				PlayerFile file2 = GetPlayerFile.getPlayerFile(on);
				if (file2.getString("channel").equalsIgnoreCase("global")) {
					e.setCancelled(false);
				}
			}
		}
		for (Race race : Main.getInstance().getManager().getRaces()) {
			if (file.getString("channel") != null && file.getString("channel").equalsIgnoreCase(race.getName())) {
				for (Player on : Bukkit.getOnlinePlayers()) {
					PlayerFile file2 = GetPlayerFile.getPlayerFile(on);
					if (file2.getString("channel").equalsIgnoreCase(file.getString("channel"))) {
						e.setCancelled(true);
						on.sendMessage(Strings.file.getString("Channels.format").replace("%channel%", file.getString("channel"))
								.replace("%msg%", e.getMessage()).replace("&", "§").replace("%player%", p.getName()));
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerRecieveChat(AsyncPlayerChatEvent e) {
		for (Player on : Bukkit.getOnlinePlayers()) {
			PlayerFile file2 = GetPlayerFile.getPlayerFile(on);
			PlayerFile file3 = GetPlayerFile.getPlayerFile(e.getPlayer());
			if (file3.getString("channel").equalsIgnoreCase("global")) {
				for (Race race : Main.getInstance().getManager().getRaces()) {
					if (file2.getString("channel").equalsIgnoreCase(race.getName())) {
						e.getRecipients().remove(on);
					}
				}
			}
		}
	}
}
