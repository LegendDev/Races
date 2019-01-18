package me.legenddev.legendraces.filemanager;

import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import java.util.List;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class PlayerFile {
	private File file;
	private YamlConfiguration yaml;

	public PlayerFile(File file) {
		this.file = null;
		this.yaml = new YamlConfiguration();
		this.file = file;
		this.load();
	}

	public PlayerFile(String path) {
		this.file = null;
		this.yaml = new YamlConfiguration();
		this.file = new File(path);
		this.load();
	}

	public void createFile() {
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException ex) {
			}
		}
	}

	private void load() {
		try {
			this.yaml.load(this.file);
		} catch (Exception ex) {
		}
	}

	public void save() {
		try {
			this.yaml.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		try {
			this.file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File getFile(String PlayerName) {
		return this.file;
	}

	public int getInt(String s) {
		return this.yaml.getInt(s);
	}

	public void reload() {
		this.save();
		this.load();
	}

	public String getString(String s) {
		return this.yaml.getString(s);
	}

	public Object get(String s) {
		return this.yaml.get(s);
	}

	public boolean getBoolean(String s) {
		return this.yaml.getBoolean(s);
	}

	public void add(String s, Object o) {
		if (!this.contains(s)) {
			this.set(s, o);
			this.save();
		}
	}

	public void addToStringList(String s, String o) {
		this.yaml.getStringList(s).add(o);
		this.save();
	}

	public void removeFromStringList(String s, String o) {
		this.yaml.getStringList(s).remove(o);
		this.save();
	}

	public List<String> getStringList(String s) {
		return (List<String>) this.yaml.getStringList(s);
	}

	public void addToIntegerList(String s, int o) {
		this.yaml.getIntegerList(s).add(o);
		this.save();
	}

	public void removeFromIntegerList(String s, int o) {
		this.yaml.getIntegerList(s).remove(o);
		this.save();
	}

	public List<Integer> getIntegerList(String s) {
		return this.yaml.getIntegerList(s);
	}

	public void createNewStringList(String s, List<String> list) {
		this.yaml.set(s, list);
		this.save();
	}

	public void createNewIntegerList(String s, List<Integer> list) {
		this.yaml.set(s, list);
		this.save();
	}

	public void remove(String s) {
		this.set(s, null);
		this.save();
	}

	public boolean contains(String s) {
		return this.yaml.contains(s);
	}

	public double getDouble(String s) {
		return this.yaml.getDouble(s);
	}

	public void set(String s, Object o) {
		this.yaml.set(s, o);
		this.save();
	}

	public ConfigurationSection getConfigurationSection(String s) {
		return this.yaml.getConfigurationSection(s);
	}

	public void createConfigurationSection(String s) {
		this.yaml.createSection(s);
		this.save();
	}

	public void increment(String s) {
		this.yaml.set(s, (this.getInt(s) + 1));
		this.save();
	}

	public void decrement(String s) {
		this.yaml.set(s, (this.getInt(s) - 1));
		this.save();
	}

	public void increment(String s, int i) {
		this.yaml.set(s, (this.getInt(s) + i));
		this.save();
	}

	public void decrement(String s, int i) {
		this.yaml.set(s, (this.getInt(s) - i));
		this.save();
	}

	public YamlConfigurationOptions options() {
		return this.yaml.options();
	}

	public boolean doesFileExist() {
		return this.file.exists();
	}

	public ItemStack getItemStack(String path) {
		return this.yaml.getItemStack(path);
	}
}
