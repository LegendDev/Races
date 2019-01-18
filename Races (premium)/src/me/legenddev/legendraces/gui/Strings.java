package me.legenddev.legendraces.gui;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.legenddev.legendraces.Main;

public class Strings {
	public static FileConfiguration file;
	public static String noperms;

	public static void loadStrings() {
		file = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "messages.yml"));
		noperms = Strings.file.getString("Messages.noperms").replace("&", "§").replace("%prefix%", Main.getPrefix());
	}
}
