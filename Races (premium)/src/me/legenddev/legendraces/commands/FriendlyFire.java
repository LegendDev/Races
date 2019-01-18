package me.legenddev.legendraces.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.gui.Strings;

public class FriendlyFire implements CommandExecutor {

	private static boolean enabled;

	public FriendlyFire() {
		enabled = Main.getInstance().getConfig().getBoolean("friendlyFire");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("races.friendlyfire"))) {
			sender.sendMessage(Strings.noperms);
			return false;
		}
		if (args.length == 0 || args.length < 1) {
			List<String> msgs = Strings.file.getStringList("Help.Messages");
			for (String s : msgs) {
				String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg);
			}
			return false;
		}
		Player p = (Player) sender;
		if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on")) {
			enabled = true;
			sendMessage(p);
		} else if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off")) {
			enabled = false;
			sendMessage(p);
		} else {
			List<String> msgs = Strings.file.getStringList("Help.Messages");
			for (String s : msgs) {
				String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg);
			}
		}
		return false;
	}

	public static boolean isFfEnabled() {
		return enabled;
	}

	private void sendMessage(Player p) {
		p.sendMessage(Strings.file.getString("Messages.toggledfriendlyfire").replace("%prefix%", Main.getPrefix())
				.replace("&", "§").replaceAll("%toggledState%", enabled ? "§aENABLED§e" : "§cDISABLED§e"));
	}

}
