package me.legenddev.legendraces.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.manager.Race;

public class RaceListener implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Race pRace = RaceAPI.getPlayerRace(p);
		if (pRace != null) {
			pRace.setDeaths(pRace.getDeaths() + 1);
		}
		if (p.getKiller() instanceof Player) {
			Player k = p.getKiller();
			Race kRace = RaceAPI.getPlayerRace(k);
			if (kRace != null) {
				kRace.setKills(kRace.getKills() + 1);
			}
		}
	}

	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		if (!(e.getEntity() instanceof Player) && e.getEntity().getKiller() instanceof Player) {
			Player p = e.getEntity().getKiller();
			Race pRace = RaceAPI.getPlayerRace(p);
			if (pRace != null) {
				pRace.setMobKills(pRace.getMobKills() + 1);
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock().getType().name().contains("ORE")) {
			Player p = e.getPlayer();
			Race race = RaceAPI.getPlayerRace(p);
			if (race != null) {
				race.setOresMined(race.getOresMined() + 1);
			}
		}
	}

}
