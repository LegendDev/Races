package me.legenddev.legendraces.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class CommandListener implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.isCancelled()) {
			return;
		}
		Race race = RaceAPI.getPlayerRace(e.getPlayer());
		if (race != null && race.getBlockedCommands() != null && !race.getBlockedCommands().isEmpty()) {
			for (String s : race.getBlockedCommands()) {
				if (e.getMessage().toLowerCase().startsWith("/" + s.toLowerCase())) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(Strings.file.getString("Messages.blockedracecommand")
							.replaceAll("%prefix%", Main.getPrefix()).replaceAll("&", "§"));
				}
			}
		}
		if (race != null && e.getMessage().equalsIgnoreCase("/spawn") && Main.getInstance().getConfig().getBoolean("overrideSpawnCommand")) {
			e.setCancelled(true);
			World w = Bukkit.getServer()
					.getWorld(Main.getPlugin().getConfig().getString("spawn." + race.getName().toLowerCase() + ".world"));
			double x = Main.getPlugin().getConfig().getDouble("spawn." + race.getName().toLowerCase() + ".x");
			double y = Main.getPlugin().getConfig().getDouble("spawn." + race.getName().toLowerCase() + ".y");
			double z = Main.getPlugin().getConfig().getDouble("spawn." + race.getName().toLowerCase() + ".z");
			float pitch = (float) Main.getPlugin().getConfig()
					.getDouble("spawn." + race.getName().toLowerCase() + ".pitch");
			float yaw = (float) Main.getPlugin().getConfig().getDouble("spawn." + race.getName().toLowerCase() + ".yaw");
			Location loc = new Location(w, x, y, z, yaw, pitch);
			e.getPlayer().teleport(loc);
			e.getPlayer().sendMessage(Strings.file.getString("Messages.teleporting").replace("%spawn%", race.getName().toLowerCase())
					.replace("%prefix%", Main.getPrefix()).replace("&", "§"));
		}
	}

}
