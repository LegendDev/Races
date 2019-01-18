package me.legenddev.legendraces.filemanager;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class GetPlayerFile {
	public static PlayerFile getPlayerFile(Player p) {
		String uuid = p.getUniqueId().toString();
		return new PlayerFile("plugins/Races/UserData/" + uuid + ".yml");
	}

	public static PlayerFile getPlayerFile(OfflinePlayer p) {
		String uuid = p.getUniqueId().toString();
		return new PlayerFile("plugins/Races/UserData/" + uuid + ".yml");
	}
}
