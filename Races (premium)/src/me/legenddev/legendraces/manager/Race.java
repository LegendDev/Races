package me.legenddev.legendraces.manager;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class Race {
	
	private boolean enabled;
	private String name, displayName;
	private Material mat;
	private int data, slot;
	private List<String> description;
	private String tag, permission;
	private Map<PotionEffectType, Integer> effects;
	private List<String> blockedCommands;
	private int kills, deaths, balance, oresMined, mobKills;
	private List<String> permissions;
	private Location cornerOne, cornerTwo;
	private Claim claim;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Material getMat() {
		return mat;
	}

	public void setMat(Material mat) {
		this.mat = mat;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public List<String> getDescription() {
		return description;
	}

	public void setDescription(List<String> description) {
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Map<PotionEffectType, Integer> getEffects() {
		return effects;
	}

	public void setEffects(Map<PotionEffectType, Integer> effects) {
		this.effects = effects;
	}

	public List<String> getBlockedCommands() {
		return blockedCommands;
	}

	public void setBlockedCommands(List<String> blockedCommands) {
		this.blockedCommands = blockedCommands;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getOresMined() {
		return oresMined;
	}

	public void setOresMined(int oresMined) {
		this.oresMined = oresMined;
	}

	public int getMobKills() {
		return mobKills;
	}

	public void setMobKills(int mobKills) {
		this.mobKills = mobKills;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public Location getCornerOne() {
		return cornerOne;
	}

	public void setCornerOne(Location cornerOne) {
		this.cornerOne = cornerOne;
	}

	public Location getCornerTwo() {
		return cornerTwo;
	}

	public void setCornerTwo(Location cornerTwo) {
		this.cornerTwo = cornerTwo;
	}

	public Claim getClaim() {
		return claim;
	}

	public void setClaim(Claim claim) {
		this.claim = claim;
	}

}
