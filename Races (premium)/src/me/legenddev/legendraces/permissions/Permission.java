package me.legenddev.legendraces.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import me.legenddev.legendraces.Main;

public class Permission {
	
	private Map<UUID, PermissionAttachment> map;
	
	public Permission() {
		this.map = new HashMap<>();
	}
	
	private PermissionAttachment get(Player player) {
		if (this.map.containsKey(player.getUniqueId())) {
			return this.map.get(player.getUniqueId());
		}
		PermissionAttachment pa = player.addAttachment(Main.getInstance());
		this.map.put(player.getUniqueId(), pa);
		return pa;
	}
	
	public void addPermission(Player player, String perm) {
		PermissionAttachment pa = get(player);
		pa.setPermission(perm, true);
		player.recalculatePermissions();
		this.map.put(player.getUniqueId(), pa);
	}
	
	public void removePermission(Player player, String perm) {
		PermissionAttachment pa = get(player);
		pa.unsetPermission(perm);
		player.recalculatePermissions();
		this.map.put(player.getUniqueId(), pa);
	}
	
	public void unattach(Player player) {
		if (this.map.containsKey(player.getUniqueId())) {
			player.removeAttachment(this.map.get(player.getUniqueId()));
			this.map.remove(player.getUniqueId());
		}
	}

}
