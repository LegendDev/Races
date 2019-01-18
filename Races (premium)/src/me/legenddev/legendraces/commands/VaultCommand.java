package me.legenddev.legendraces.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;
import me.legenddev.legendraces.vaults.VaultAPI;

public class VaultCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Players only.");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("races.vault")) {
			p.sendMessage(Strings.noperms);
			return true;
		}
		Race race = RaceAPI.getPlayerRace(p);
		if (race == null) {
			p.sendMessage(Strings.file.getString("Messages.havenorace").replace("&", "§").replace("%prefix%", Main.getPrefix()));
			return true;
		}
		VaultAPI.loadVault(p);
		p.sendMessage(Strings.file.getString("Messages.openingvault").replace("&", "§").replace("%prefix%", Main.getPrefix()));
		return false;
	}

}
