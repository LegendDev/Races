package me.legenddev.legendraces.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.manager.Race;

public class ChatListener implements Listener {
	Main plugin;

	public ChatListener(Main i) {
		this.plugin = i;
	}

	public String raceTag(Player p) {
		PlayerFile file = GetPlayerFile.getPlayerFile(p);
		if (!Main.getPlugin().getConfig().getString("enableTags").equalsIgnoreCase("true")
				|| file.getString("race") == null) {
			return "";
		}
		if (file.get("race") != null) {
			Race race = Main.getInstance().getManager().getRaceByName(file.getString("race"));
			return ChatColor.translateAlternateColorCodes('&', race.getTag());
		}
		return "";
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String format = e.getFormat();
		if (plugin.getConfig().getBoolean("usePlaceholder")) {
			e.setFormat(format.replace("{RACE}", this.raceTag(p)));
		} else {
			format = String.valueOf(this.raceTag(p)) + format;
			e.setFormat(format);
		}
	}

}
