package me.legenddev.legendraces.events;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;

public class PlaceholderAPIHook extends EZPlaceholderHook {

	public PlaceholderAPIHook(Plugin plugin, String identifier) {
		super(plugin, "races");
	}

	@Override
	public String onPlaceholderRequest(Player p, String string) {
		if (p == null) {
			return "";
		}
		if (string.equals("prefix")) {
			return Main.getPrefix();
		}
		if (string.equals("player_race")) {
			PlayerFile file = GetPlayerFile.getPlayerFile(p);
			if (file.getString("race") == null) {
				return "";
			}
			return Main.getInstance().getManager().getRaceByName(file.getString("race")).getName();
		}
		if (string.equals("player_race_tag")) {
			PlayerFile file = GetPlayerFile.getPlayerFile(p);
			if (file.getString("race") == null) {
				return "";
			}
			return Main.getInstance().getManager().getRaceByName(file.getString("race")).getTag();
		}
		if (string.equals("player_race_isEnabled")) {
			PlayerFile file = GetPlayerFile.getPlayerFile(p);
			if (file.getString("race") == null) {
				return "";
			}
			return Main.getInstance().getManager().getRaceByName(file.getString("race")).isEnabled() ? "Yes" : "No";
		}
		return null;
	}

}