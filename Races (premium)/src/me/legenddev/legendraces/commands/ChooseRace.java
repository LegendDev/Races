package me.legenddev.legendraces.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.GUI;
import me.legenddev.legendraces.gui.Strings;

public class ChooseRace implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You are not a player.");
			return false;
		}
		if (!(sender.hasPermission("races.chooserace"))) {
			sender.sendMessage(Strings.noperms);
			return false;
		}
		Player p = (Player) sender;
		PlayerFile file = GetPlayerFile.getPlayerFile(p);
		if (file.getString("race") == null) {
			p.openInventory(GUI.main(p));
			return true;
		} else {
			p.sendMessage(Strings.file.getString("Messages.alreadyhaverace").replace("%prefix%", Main.getPrefix())
					.replace("&", "§"));
		}
		return true;
	}

}
