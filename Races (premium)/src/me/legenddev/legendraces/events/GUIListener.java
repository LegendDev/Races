package me.legenddev.legendraces.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.api.RaceAPI;
import me.legenddev.legendraces.filemanager.GetPlayerFile;
import me.legenddev.legendraces.filemanager.PlayerFile;
import me.legenddev.legendraces.gui.GUI;
import me.legenddev.legendraces.gui.Strings;
import me.legenddev.legendraces.manager.Race;

public class GUIListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getName().equals(GUI.main(p).getName()) || e.getInventory().getName().equals(Strings.file.getString("TopRaces.gui.title").replace("&", "§"))) {
			e.setCancelled(true);
		}
		if (e.getCurrentItem() == null || e.getCurrentItem().getItemMeta() == null
				|| e.getCurrentItem().getItemMeta().getDisplayName() == null) {
			return;
		}
		PlayerFile config = GetPlayerFile.getPlayerFile(p);
		String name = e.getCurrentItem().getItemMeta().getDisplayName();
		for (Race race : Main.getInstance().getManager().getRaces()) {
			if (race.getDisplayName().replace('&', '§').equalsIgnoreCase(name)) {
				if (race.isEnabled()) {
					if (p.hasPermission(race.getPermission())) {
						config.set("race", race.getName());
						config.set("channel", "global");
						config.save();
						p.closeInventory();
						if (Main.getPlugin().getConfig().getString("doCommands").equalsIgnoreCase("true")) {
							List<String> cmds = Main.getPlugin().getConfig().getStringList("commands");
							for (String s : cmds) {
								String cmd = s.replace("%player%", p.getName()).replace("/", "").replace("%race%",
										race.getName());
								Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
							}
						}
						for (int i = 0; i < 200; ++i) {
							p.sendMessage(" ");
						}
						for (Player player : Bukkit.getOnlinePlayers()) {
							player.sendMessage(Strings.file.getString("Broadcasts.raceSelect")
									.replace("%prefix%", Main.getPrefix()).replace("%player%", p.getName())
									.replace("&", "§").replace("%race%", race.getName()));
						}
						p.sendMessage(Strings.file.getString("Messages.selected").replace("&", "§")
								.replace("%prefix%", Main.getPrefix()).replace("%race%", race.getName()));
						Main.getInstance().permissions.addPermissions(p.getUniqueId());
						if (Main.getInstance().getConfig().getBoolean("fireworkEnabled")) {
							Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(), new BukkitRunnable() {
								@Override
								public void run() {
									Firework f = (Firework) p.getWorld().spawn(p.getLocation(), Firework.class);
									FireworkMeta fm = f.getFireworkMeta();

									fm.addEffect(RaceAPI.getRandomEffect());

									f.setFireworkMeta(fm);
								}
							}, 20L);
						}
						/*if (Main.getInstance().getManager().getRacesAmounts().containsKey(race)) {
							int i = Main.getInstance().getManager().getRacesAmounts().get(race);
							Main.getInstance().getManager().getRacesAmounts().remove(race);
							Main.getInstance().getManager().getRacesAmounts().put(race, i + 1);
							racesConf.set("races." + race.getName() + ".currentAmount", i + 1);
							try {
								racesConf.save(Main.racesf);
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}*/
					} else {
						p.closeInventory();
						p.sendMessage(Strings.noperms);
					}
				} else {
					p.closeInventory();
					p.sendMessage(Strings.file.getString("Messages.racedisabled").replace("%prefix%", Main.getPrefix())
							.replace("&", "§").replace("%race%", race.getName()));
				}
			}
		}
	}

	@EventHandler
	public void InvCloseEvent(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (e.getInventory().getName().equals(GUI.main(p).getName())) {
			PlayerFile config = GetPlayerFile.getPlayerFile(p);
			if (config.getString("race") == null) {
				new BukkitRunnable() {
					public void run() {
						e.getPlayer().openInventory(GUI.main(p));
					}
				}.runTaskLater(Main.getPlugin(), 5L);
			}
		}
	}
}
