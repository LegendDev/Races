package me.legenddev.legendraces.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.commands.FriendlyFire;
import me.legenddev.legendraces.manager.Race;

public class DamageListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			Race pRace = RaceAPI.getPlayerRace(p);
			Race dRace = RaceAPI.getPlayerRace(d);
			if (pRace == dRace) {
				if (FriendlyFire.isFfEnabled()) {
					e.setCancelled(false);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}

}
