package me.legenddev.legendraces.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class RaceDeposit implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only.");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.deposit")) {
			p.sendMessage(Strings.noperms);
			return true;
		}
		if (args.length == 0) {
			List<String> msgs = Strings.file.getStringList("Help.Messages");
			for (String s : msgs) {
				String msg = s.replace("%prefix%", Main.getPrefix()).replace("&", "§");
				sender.sendMessage(msg);
			}
		} else if (args.length == 1) {
			Race race = RaceAPI.getPlayerRace(p);
			if (race == null) {
				p.sendMessage(Strings.file.getString("Messages.havenorace").replace("%prefix%", Main.getPrefix())
						.replace("&", "§"));
				return true;
			}
			double balance = Main.getInstance().getEconomy().getBalance(p);
			int price = 0;
			try {
				price = Integer.valueOf(args[0]);
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
			if (balance >= price) {
				Main.getInstance().getEconomy().withdrawPlayer(p, price);
				race.setBalance(race.getBalance() + price);
				p.sendMessage(Strings.file.getString("Messages.moneydeposited").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replaceAll("%amount%", args[0]));
			} else {
				p.sendMessage(Strings.file.getString("Messages.notenoughmoney").replace("%prefix%", Main.getPrefix())
						.replace("&", "§"));
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

}
