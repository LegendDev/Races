package me.legenddev.legendraces.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.GUI;

public class Events implements Listener {
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Main.getInstance().inTerritory.put(p, "none");
		if (!Main.getInstance().getConfig().getString("skipGuiOnJoin").equals("true")) {
			PlayerFile config = GetPlayerFile.getPlayerFile(p);
			if (!p.hasPlayedBefore()) {
				new BukkitRunnable() {
					public void run() {
						p.openInventory(GUI.main(p));
					}
				}.runTaskLater(Main.getPlugin(), 5L);
			}
			if (p.hasPlayedBefore() && config.getString("race") == null) {
				new BukkitRunnable() {
					public void run() {
						p.openInventory(GUI.main(p));
					}
				}.runTaskLater(Main.getPlugin(), 5L);
			} else if (p.hasPlayedBefore() && config.getString("race") != null) {
				Main.getInstance().permissions.addPermissions(p.getUniqueId());
			}
		}
	}

	@EventHandler
	public void i(PlayerJoinEvent e) {
		PlayerFile config = GetPlayerFile.getPlayerFile(e.getPlayer());
		if (!config.doesFileExist()) {
			config.createFile();
		}
	}
}