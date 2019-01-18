package me.legenddev.legendraces.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.leaderboards.TopGUI;
import me.legenddev.legendraces.leaderboards.TopUtils;
import me.legenddev.legendraces.manager.Race;

public class TopRaces implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only.");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.topraces")) {
			p.sendMessage(Strings.noperms);
			return true;
		}
		List<Race> topRaces = TopUtils.getTopRaces();
		String s = Main.getInstance().getConfig().getString("TopRacesCommand");
		switch (s.toLowerCase()) {
		case "gui": {
			new TopGUI(p, topRaces);
			return true;
		}
		case "message": {
			sendTopRacesMessage(p, topRaces);
			return true;
		}
		default: {
			sendTopRacesMessage(p, topRaces);
		}
		}
		return true;
	}

	private void sendTopRacesMessage(Player p, List<Race> topRaces) {
		List<Race> races = new ArrayList<>();
		topRaces.forEach(race -> races.add(race));
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', Strings.file.getString("TopRaces.messages.topline")));
		for (Race race : races) {
			String s = Strings.file.getString("TopRaces.messages.raceposition");
			s = s.replace("{number}", (topRaces.indexOf(race) + 1) + "");
			s = s.replace("{name}", race.getName());
			s = s.replace("{balance}", race.getBalance() + "");
			s = s.replace("{kills}", race.getKills() + "");
			s = s.replace("{deaths}", race.getDeaths() + "");
			s = s.replace("{oresMined}", race.getOresMined() + "");
			s = s.replace("{mobKills}", race.getMobKills() + "");
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
		}
	}

}
