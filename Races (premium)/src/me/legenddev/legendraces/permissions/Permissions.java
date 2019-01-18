package me.legenddev.legendraces.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.manager.Race;

public class Permissions {
	
	public List<String> getPermissions(Race race) {
		List<String> perms = new ArrayList<>();
		perms.addAll(race.getPermissions());
		return perms;
	}
	
	@SuppressWarnings("unused")
	private void removePerms(UUID id) {
		Player player = Bukkit.getPlayer(id);
		for (PermissionAttachmentInfo pAI : player.getEffectivePermissions()) {
			Main.getInstance().permission.removePermission(player, pAI.getPermission());
		}
	}
	
	public void addPermissions(UUID id) {
		Player player = Bukkit.getPlayer(id);
		Race race = RaceAPI.getPlayerRace(player);
		if (race == null) return;
		if (race.getPermissions() == null || race.getPermissions().isEmpty()) return;
		List<String> permissions = getPermissions(race);
		for (String perm : permissions) {
			Main.getInstance().permission.addPermission(player, perm);
		}
		player.recalculatePermissions();
	}

}
