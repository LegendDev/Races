package me.legenddev.legendraces.filemanager;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAccessor {
	private String fileName;
	private JavaPlugin plugin;
	private File configFile;
	private FileConfiguration fileConfiguration;

	public ConfigAccessor(JavaPlugin plugin, String fileName) {
		if (plugin == null) {
			throw new IllegalArgumentException("plugin cannot be null");
		}
		this.plugin = plugin;
		this.fileName = fileName;
		File dataFolder = plugin.getDataFolder();
		if (dataFolder == null) {
			throw new IllegalStateException();
		}
		this.configFile = new File(plugin.getDataFolder(), fileName);
	}

	public void reloadConfig() {
		this.fileConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(this.configFile);
		Closeable defConfigStream = this.plugin.getResource(this.fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration((File) defConfigStream);
			this.fileConfiguration.setDefaults((Configuration) defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (this.fileConfiguration == null) {
			this.reloadConfig();
		}
		return this.fileConfiguration;
	}

	public void saveConfig() {
		if (this.fileConfiguration == null || this.configFile == null) {
			return;
		}
		try {
			this.getConfig().save(this.configFile);
		} catch (IOException ex) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
		}
	}

	public void saveDefaultConfig() {
		if (!this.configFile.exists()) {
			this.plugin.saveResource(this.fileName, false);
		}
	}
}
