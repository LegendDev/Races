package me.legenddev.legendraces.leaderboards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.legenddev.legendraces.Main;
import me.legenddev.legendraces.manager.Race;

public class TopUtils {

	static Comparator<Race> comparator = new Comparator<Race>() {
		public int compare(Race raceOne, Race raceTwo) {
			String s = Main.getInstance().getConfig().getString("SortTopRacesBy");
			switch (s.toLowerCase()) {
			case "kills": {
				return raceTwo.getKills() - raceOne.getKills();
			}
			case "deaths": {
				return raceTwo.getDeaths() - raceOne.getDeaths();
			}
			case "balance": {
				return raceTwo.getBalance() - raceOne.getBalance();
			}
			case "oresmined": {
				return raceTwo.getOresMined() - raceOne.getOresMined();
			}
			case "mobkills": {
				return raceTwo.getMobKills() - raceOne.getMobKills();
			}
			default: {
				break;
			}
			}
			return raceTwo.getKills() - raceOne.getKills();
		}
	};

	public static List<Race> getTopRaces() {
		List<Race> races = new ArrayList<>();
		Main.getInstance().getManager().getRaces().forEach(race -> races.add(race));
		Collections.sort(races, comparator);
		return races;
	}

}
