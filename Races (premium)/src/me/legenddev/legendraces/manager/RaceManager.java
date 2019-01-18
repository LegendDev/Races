package me.legenddev.legendraces.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffectType;

import me.legenddev.legendraces.Main;

public class RaceManager {

	private static Map<String, Race> racesMap;
	private List<Race> races;
	private Map<Race, Integer> raceAmounts;

	public RaceManager() {
		racesMap = new HashMap<>();
		races = new ArrayList<>();
		raceAmounts = new HashMap<>();
		loadRaces();
	}

	public Map<String, Race> getRacesMap() {
		return racesMap;
	}

	public List<Race> getRaces() {
		return races;
	}

	public Map<Race, Integer> getRacesAmounts() {
		return raceAmounts;
	}

	public void loadRaces() {
		FileConfiguration config = Main.getInstance().getRaces();
		if (config.getConfigurationSection("races") == null) {
			Bukkit.getLogger().info("No races are specified inside of the Races configuration.");
			return;
		}
		for (String race : config.getConfigurationSection("races").getKeys(false)) {
			Race r = new Race();
			if (config.getString("races." + race + ".enabled") != null) {
				r.setEnabled(Boolean.valueOf(config.getString("races." + race + ".enabled")));
			}
			if (config.getString("races." + race + ".name") != null) {
				r.setName(config.getString("races." + race + ".name"));
			}
			if (config.getString("races." + race + ".icon.displayName") != null) {
				r.setDisplayName(config.getString("races." + race + ".icon.displayName"));
			}
			if (config.getString("races." + race + ".icon.material") != null) {
				r.setMat(Material.valueOf(config.getString("races." + race + ".icon.material").toUpperCase()));
			}
			if (config.getString("races." + race + ".icon.material") != null) {
				r.setData(config.getInt("races." + race + ".icon.data"));
			}
			if (config.getString("races." + race + ".slot") != null) {
				r.setSlot(config.getInt("races." + race + ".slot"));
			}
			if (config.getStringList("races." + race + ".description") != null) {
				r.setDescription(config.getStringList("races." + race + ".description"));
			}
			if (config.getString("races." + race + ".tag") != null) {
				r.setTag(config.getString("races." + race + ".tag"));
			}
			if (config.getString("races." + race + ".permission") != null) {
				r.setPermission(config.getString("races." + race + ".permission"));
			}
			if (config.getStringList("races." + race + ".effects") != null) {
				Map<PotionEffectType, Integer> effects = new HashMap<>();
				for (String s : config.getStringList("races." + race + ".effects")) {
					String label = s.split(" ")[0].toUpperCase();
					PotionEffectType type = PotionEffectType.getByName(label);
					if (type == null)
						continue;
					int level;
					try {
						level = Integer.parseInt(s.split(" ")[1]) - 1;
					} catch (Exception ex) {
						continue;
					}
					effects.put(type, level);
				}
				r.setEffects(effects);
			}
			if (config.getStringList("races." + race + ".blockedCommands") != null) {
				List<String> blockedCommands = new ArrayList<>();
				for (String s : config.getStringList("races." + race + ".blockedCommands")) {
					blockedCommands.add(s);
				}
				r.setBlockedCommands(blockedCommands);
			}
			if (config.getString("races." + race + ".kills") != null) {
				r.setKills(config.getInt("races." + race + ".kills"));
			} else {
				r.setKills(0);
			}
			if (config.getString("races." + race + ".deaths") != null) {
				r.setDeaths(config.getInt("races." + race + ".deaths"));
			} else {
				r.setDeaths(0);
			}
			if (config.getString("races." + race + ".balance") != null) {
				r.setBalance(config.getInt("races." + race + ".balance"));
			} else {
				r.setBalance(0);
			}
			if (config.getString("races." + race + ".oresMined") != null) {
				r.setOresMined(config.getInt("races." + race + ".oresMined"));
			} else {
				r.setOresMined(0);
			}
			if (config.getString("races." + race + ".mobKills") != null) {
				r.setMobKills(config.getInt("races." + race + ".mobKills"));
			} else {
				r.setMobKills(0);
			}
			if (config.getStringList("races." + race + ".permissions") != null) {
				List<String> permissions = new ArrayList<>();
				for (String s : config.getStringList("races." + race + ".permissions")) {
					permissions.add(s);
				}
				r.setPermissions(permissions);
			}
			if (config.getString("races." + race + ".cornerOne") != null) {
				r.setCornerOne(getLocation(config.getString("races." + race + ".cornerOne")));
			}
			if (config.getString("races." + race + ".cornerTwo") != null) {
				r.setCornerTwo(getLocation(config.getString("races." + race + ".cornerTwo")));
			}
			if (r.getCornerOne() != null && r.getCornerTwo() != null) {
				r.setClaim(new Claim(r, r.getCornerOne(), r.getCornerTwo()));
			}
			racesMap.put(r.getName(), r);
			races.add(r);
			raceAmounts.put(r, config.getInt("races." + race + ".currentAmount"));
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void reloadRaces() {
		Iterator<Race> it = races.iterator();
		while (it.hasNext()) {
			Race r = it.next();
			if (racesMap.containsKey(r.getName())) {
				racesMap.remove(r.getName());
			}
			if (raceAmounts.containsKey(r.getName())) {
				raceAmounts.remove(r);
			}
			it.remove();
		}
		loadRaces();
	}

	public void saveData() {
		FileConfiguration config = Main.getInstance().getRaces();
		if (config.getConfigurationSection("races") == null) {
			Bukkit.getLogger().info("No races are specified inside of the Races configuration.");
			return;
		}
		for (Race race : getRaces()) {
			config.set("races." + race.getName() + ".kills", race.getKills());
			config.set("races." + race.getName() + ".deaths", race.getDeaths());
			config.set("races." + race.getName() + ".balance", race.getBalance());
			config.set("races." + race.getName() + ".oresMined", race.getOresMined());
			config.set("races." + race.getName() + ".mobKills", race.getMobKills());
			try {
				config.save(Main.racesf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Race getRaceByName(String s) {
		return racesMap.get(s);
	}

	public String getStringofLocation(Location loc) {
		return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
	}

	public static Location getLocation(String what) {
		String[] split = what.split(",");
		return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]),
				Double.parseDouble(split[3]));
	}
	
	public Race getRaceFromLocation(Location location) {
		for (Race race : getRaces()) {
			if (race.getClaim() == null) {
				continue;
			}
			if (race.getClaim().isInside(location)) {
				return race;
			}
		}
		return null;
	}

}
