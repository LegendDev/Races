package me.legenddev.legendraces.vaults;

import java.io.File;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.manager.Race;

public class VaultManager {
	
	public File getVaultFile(Race race) {
        File folder = new File(Main.getInstance().getDataFolder(), "vaults");
        File raceFile = new File(Main.getInstance().getDataFolder(), "vaults" + File.separator + "" + race.getName().toString() + ".yml");
        if(!folder.exists()) {
            try {
                folder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(!raceFile.exists()) {
            try {
                raceFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return raceFile;
    }

}
