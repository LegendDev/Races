package me.legenddev.legendraces.api;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.manager.Race;

public class RaceAPI {
	
	/**
	 * Get a List<Race> of Races loaded on the server
	 * Recommended to check if the List is null
	 */
	public static List<Race> getRaces() {
		return Main.getInstance().getManager().getRaces();
	}
	
	/**
	 * Get a Map<String, Race> of Races loaded on the server
	 * Recommended to check if the Map is empty
	 * String = Races name
	 * Race = The Race object
	 */
	public static Map<String, Race> getRacesMap() {
		return Main.getInstance().getManager().getRacesMap();
	}
	
	/**
	 * Get a Map<Race, Integer> of the amount of players in EACH race
	 * Recommended to check is the Map is empty
	 * Race = The Race object
	 * Integer = The Amount it has
	 */
	public static Map<Race, Integer> getRacesAmounts() {
		return Main.getInstance().getManager().getRacesAmounts();
	}
	
	/**
	 * Get the amount of members in a SPECIFIC Race has
	 */
	public static int getRaceAmount(Race race) {
		return getRacesAmounts().get(race);
	}
	
	/**
	 * Get a Race by their name
	 * race:
	 *   name: raceName
	 */
	public static Race getRaceByString(String race) {
		return Main.getInstance().getManager().getRaceByName(race);
	}
	
	/**
	 * Get a player's file
	 * Use this to get access to their information
	 */
	public static PlayerFile getPlayerFile(Player p) {
		return GetPlayerFile.getPlayerFile(p);
	}
	
	/**
	 * Get an OfflinePlayer's file
	 * Used when a server is not in Online mode
	 */
	public static PlayerFile getPlayerFile(OfflinePlayer p) {
		return GetPlayerFile.getPlayerFile(p);
	}
	
	/**
	 * Get a player's race
	 * Use this to see what race a player has
	 */
	public static Race getPlayerRace(Player p) {
		PlayerFile file = getPlayerFile(p);
		return getRaceByString(file.getString("race"));
	}
	
	/**
	 * Get an OfflinePlayer's race
	 * Used when a server is not in Online mode
	 */
	public static Race getPlayerRace(OfflinePlayer p) {
		PlayerFile file = getPlayerFile(p);
		return getRaceByString(file.getString("race"));
	}
	
	/** 
	 * Just for the lols
	 */
	private static Color getColor(int i) {
		Color c = null;
		if (i == 1) {
			c = Color.AQUA;
		}
		if (i == 2) {
			c = Color.BLACK;
		}
		if (i == 3) {
			c = Color.BLUE;
		}
		if (i == 4) {
			c = Color.FUCHSIA;
		}
		if (i == 5) {
			c = Color.GRAY;
		}
		if (i == 6) {
			c = Color.GREEN;
		}
		if (i == 7) {
			c = Color.LIME;
		}
		if (i == 8) {
			c = Color.MAROON;
		}
		if (i == 9) {
			c = Color.NAVY;
		}
		if (i == 10) {
			c = Color.OLIVE;
		}
		if (i == 11) {
			c = Color.ORANGE;
		}
		if (i == 12) {
			c = Color.PURPLE;
		}
		if (i == 13) {
			c = Color.RED;
		}
		if (i == 14) {
			c = Color.SILVER;
		}
		if (i == 15) {
			c = Color.TEAL;
		}
		if (i == 16) {
			c = Color.WHITE;
		}
		if (i == 17) {
			c = Color.YELLOW;
		}
		return c;
	}
	/** 
	 * for the lols
	 * 
	 * again
	 */
	public static FireworkEffect getRandomEffect() {
		Random r = new Random();

		int rt = r.nextInt(4) + 1;

		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) {
			type = FireworkEffect.Type.BALL;
		}
		if (rt == 2) {
			type = FireworkEffect.Type.BALL_LARGE;
		}
		if (rt == 3) {
			type = FireworkEffect.Type.BURST;
		}
		if (rt == 4) {
			type = FireworkEffect.Type.CREEPER;
		}
		if (rt == 5) {
			type = FireworkEffect.Type.STAR;
		}
		int r1i = r.nextInt(17) + 1;

		int r2i = r.nextInt(17) + 1;

		Color c1 = getColor(r1i);

		Color c2 = getColor(r2i);

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type)
				.trail(r.nextBoolean()).build();

		return effect;
	}
	
}
