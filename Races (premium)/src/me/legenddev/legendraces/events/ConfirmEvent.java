package me.legenddev.legendraces.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.GUI;
import me.legenddev.legendraces.gui.Strings;

public class ConfirmEvent implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (!e.getInventory().getName().equals(GUI.confirm(p).getName())) {
			return;
		}
		if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null
				|| e.getCurrentItem().getItemMeta().getDisplayName() == null) {
			return;
		}
		e.setCancelled(true);
		String dname = e.getCurrentItem().getItemMeta().getDisplayName();
		if (dname.equals(Strings.file.get("ConfirmGui.confirmItem.displayName".replace('&', '§')))) {
			PlayerFile file = GetPlayerFile.getPlayerFile(p);
			file.set("race", null);
			file.set("racechanges", 0);
			file.save();
			p.openInventory(GUI.main(p));
		}
		if (dname.equals(Strings.file.get("ConfirmGui.denyItem.displayName".replace('&', '§')))) {
			p.closeInventory();
		}
	}

}
