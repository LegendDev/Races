package me.legenddev.legendraces.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.gui.Strings;

public class TerritoryListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location to = e.getTo();
		Location from = e.getFrom();
		if (to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ()) {
			Player player = e.getPlayer();
			if (Main.getInstance().getManager().getRaceFromLocation(from) == null && Main.getInstance().getManager().getRaceFromLocation(to) != null) {
				String race = Main.getInstance().getManager().getRaceFromLocation(to).getName();
				player.sendMessage(Strings.file.getString("Messages.enterringterritory").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replace("%race%", race));
			} else if (Main.getInstance().getManager().getRaceFromLocation(from) != null && Main.getInstance().getManager().getRaceFromLocation(to) == null) {
				String race = Main.getInstance().getManager().getRaceFromLocation(from).getName();
				player.sendMessage(Strings.file.getString("Messages.leavingterritory").replace("%prefix%", Main.getPrefix())
						.replace("&", "§").replace("%race%", race));
			}
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getItem() == null || !e.getItem().hasItemMeta() || !e.getItem().getItemMeta().hasDisplayName()) {
			return;
		}
		if (e.getItem().getItemMeta().getDisplayName().contains("Territory Manager")) {
			if (Main.getInstance().claiming.containsKey(e.getPlayer().getUniqueId().toString())) {
				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					Main.getInstance().corners.get(e.getPlayer().getUniqueId().toString()).set(0,
							e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(String.valueOf(ChatColor.GREEN + "Corner 1 has been set!"));
				} else if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					Main.getInstance().corners.get(e.getPlayer().getUniqueId().toString()).set(1,
							e.getClickedBlock().getLocation());
					e.getPlayer().sendMessage(String.valueOf(ChatColor.GREEN + "Corner 2 has been set!"));
				}
				e.setCancelled(true);
			}
		}
	}

}
