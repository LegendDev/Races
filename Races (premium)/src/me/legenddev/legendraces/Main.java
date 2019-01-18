package me.legenddev.legendraces;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.legenddev.legendraces.commands.ChangeRace;
import me.legenddev.legendraces.commands.ChannelsDynamic;
import me.legenddev.legendraces.commands.ChannelsStatic;
import me.legenddev.legendraces.commands.ChooseRace;
import me.legenddev.legendraces.commands.Commands;
import me.legenddev.legendraces.commands.FriendlyFire;
import me.legenddev.legendraces.commands.GiveAll;
import me.legenddev.legendraces.commands.RaceDeposit;
import me.legenddev.legendraces.commands.RaceStats;
import me.legenddev.legendraces.commands.RaceTerritory;
import me.legenddev.legendraces.commands.SetRace;
import me.legenddev.legendraces.commands.Spawn;
import me.legenddev.legendraces.commands.TopRaces;
import me.legenddev.legendraces.commands.VaultCommand;
import me.legenddev.legendraces.commands.WhatRace;
import me.legenddev.legendraces.events.ChatListener;
import me.legenddev.legendraces.events.CommandListener;
import me.legenddev.legendraces.events.ConfirmEvent;
import me.legenddev.legendraces.events.DamageListener;
import me.legenddev.legendraces.events.Events;
import me.legenddev.legendraces.events.GUIListener;
import me.legenddev.legendraces.events.PlaceholderAPIHook;
import me.legenddev.legendraces.events.RaceListener;
import me.legenddev.legendraces.events.TerritoryListener;
import me.legenddev.legendraces.filemanager.ConfigAccessor;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;
import me.legenddev.legendraces.manager.RaceManager;
import me.legenddev.legendraces.permissions.Permission;
import me.legenddev.legendraces.permissions.Permissions;
import me.legenddev.legendraces.vaults.VaultAPI;
import me.legenddev.legendraces.vaults.VaultManager;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	private static ConfigAccessor MessagesConfig;
	private static FileConfiguration races;
	private RaceManager manager;
	private VaultManager vaultManager;
	private static Main instance;
	public static File racesf;
	public static Economy economy = null;
	public Permission permission;
	public Permissions permissions;
	public Map<String, Location> claiming;
	public Map<String, List<Location>> corners;
	public Map<Player, String> inTerritory;
	
	public void onEnable() {
		getServer().getConsoleSender().sendMessage("Enabling Races...");
		if (!new File(this.getDataFolder() + File.separator + "races.yml").exists()) {
			saveResource("races.yml", false);
		}
		racesf = new File(getDataFolder(), "races.yml");
		races = new YamlConfiguration();
		try {
			races.load(racesf);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		instance = this;
		manager = new RaceManager();
		vaultManager = new VaultManager();
		permission = new Permission();
		permissions = new Permissions();
		claiming = new HashMap<>();
		corners = new HashMap<>();
		inTerritory = new HashMap<>();
		(MessagesConfig = (new ConfigAccessor(this, "messages.yml"))).saveDefaultConfig();
		FileConfiguration cfg = getConfig();
		cfg.options().copyDefaults(true);
		saveDefaultConfig();
		registerEvents();
		Commands commands = new Commands();
		getCommand("changerace").setExecutor(commands);
		getCommand("race").setExecutor(commands);
		getCommand("addracechanges").setExecutor(new ChangeRace());
		Spawn spawn = new Spawn();
		getCommand("setracespawn").setExecutor(spawn);
		getCommand("racespawn").setExecutor(spawn);
		getCommand("delracespawn").setExecutor(spawn);
		getCommand("setrace").setExecutor(new SetRace());
		getCommand("whatrace").setExecutor(new WhatRace());
		getCommand("chooserace").setExecutor(new ChooseRace());
		getCommand("friendlyfire").setExecutor(new FriendlyFire());
		getCommand("racestats").setExecutor(new RaceStats());
		getCommand("racegiveall").setExecutor(new GiveAll());
		getCommand("racevault").setExecutor(new VaultCommand());
		getCommand("topraces").setExecutor(new TopRaces());
		getCommand("raceterritory").setExecutor(new RaceTerritory());
		if (getConfig().getString("channeltype").equalsIgnoreCase("static")) {
			getCommand("channel").setExecutor(new ChannelsStatic());
		}
		if (getConfig().getString("channeltype").equalsIgnoreCase("Dynamic")) {
			getCommand("channel").setExecutor(new ChannelsDynamic());
		}
		Strings.loadStrings();
		new BukkitRunnable() {
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					PlayerFile config = GetPlayerFile.getPlayerFile(player);
					if (config.getString("race") != null) {
						Race race = getManager().getRaceByName(config.getString("race"));
						if (race != null) {
							if (!race.getEffects().isEmpty()) {
								for (PotionEffectType t : race.getEffects().keySet()) {
									player.addPotionEffect(new PotionEffect(t, 160, race.getEffects().get(t)), true);
								}
							}
						}
					}
				}
			}
		}.runTaskTimer(this, 60L, 20 * 6L);
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			getServer().getConsoleSender().sendMessage("Races successfully hooked into PlaceholderAPI.");
			new PlaceholderAPIHook(this, "races").hook();
		}
		if (!setupEconomy()) {
			getServer().getConsoleSender().sendMessage("Vault was not found!");
			getServer().getConsoleSender().sendMessage("The balances for Races will not be able to be used.");
		} else {
			getServer().getConsoleSender().sendMessage("Races successfully hooked into Vault.");
			getCommand("racedeposit").setExecutor(new RaceDeposit());
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			permissions.addPermissions(player.getUniqueId());
		}
		getServer().getConsoleSender().sendMessage("Enabled Races!");
	}

	public void onDisable() {
		getManager().saveData();
		VaultAPI.saveVaults();
	}

	public static Main getInstance() {
		return instance;
	}

	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("Races");
	}
	
	public RaceManager getManager() {
		return manager;
	}

	public VaultManager getVaultManager() {
		return vaultManager;
	}

	public static ConfigAccessor getMessagesConfig() {
		return MessagesConfig;
	}

	public FileConfiguration getRaces() {
		return races;
	}
	
	public Economy getEconomy() {
		return economy;
	}
	
	public void saveRaces() {
		try {
			getRaces().save(racesf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPrefix() {
		return Strings.file.getString("Prefix").replace("&", "§");
	}

	public static ChatColor primary() {
		return ChatColor.GOLD;
	}

	public static ChatColor secondary() {
		return ChatColor.YELLOW;
	}

	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Events(), this);
		pm.registerEvents(new GUIListener(), this);
		pm.registerEvents(new ConfirmEvent(), this);
		if (getConfig().getString("channeltype").equalsIgnoreCase("static")) {
			pm.registerEvents(new ChannelsStatic(), this);
		}
		if (getConfig().getString("channeltype").equalsIgnoreCase("dynamic")) {
			pm.registerEvents(new ChannelsDynamic(), this);
		}
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new CommandListener(), this);
		pm.registerEvents(new DamageListener(), this);
		pm.registerEvents(new RaceListener(), this);
		pm.registerEvents(new TerritoryListener(), this);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	public static Boolean isInitiated() {
		return getPlugin() != null ? true : false;
	}
	
	public static final String uid = "%%__USER__%%";
	public static final String rid = "%%__RESOURCE__%%";
	public static final String nonce = "%%__NONCE__%%";

}
