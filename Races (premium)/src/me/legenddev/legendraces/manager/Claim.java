package me.legenddev.legendraces.manager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

public class Claim {

	private static Set<Claim> claimSet = new HashSet<>();

	private Race race;
	private Location[] corners;

	public Claim(Race race, Location[] corners) {
		this.race = race;
		this.corners = corners;
		claimSet.add(this);
	}

	public Race getFaction() {
		return race;
	}

	public Location getCorner1() {
		return corners[0];
	}

	public Location getCorner2() {
		return corners[1];
	}

	public Location getCorner3() {
		return new Location(corners[0].getWorld(), corners[0].getBlockX(),
				0, corners[1].getBlockZ());
	}

	public Location getCorner4() {
		return new Location(corners[0].getWorld(), corners[1].getBlockX(),
				0, corners[0].getBlockZ());
	}

	public boolean isInside(Location location) {
		int minX = Math.min(getCorner1().getBlockX(), getCorner2().getBlockX());
		int maxX = Math.max(getCorner1().getBlockX(), getCorner2().getBlockX());
		int minZ = Math.min(getCorner1().getBlockZ(), getCorner2().getBlockZ());
		int maxZ = Math.max(getCorner1().getBlockZ(), getCorner2().getBlockZ());
		return ((location.getBlockX() >= minX && location.getBlockX() <= maxX)
				&& (location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ));
	}

	public Claim(Race faction, Location corner1, Location corner2) {
		this(faction, new Location[] { corner1, corner2 });
	}

	public static Set<Claim> getClaimSet() {
		return claimSet;
	}

}