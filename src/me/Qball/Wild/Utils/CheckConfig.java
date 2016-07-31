package me.Qball.Wild.Utils;

import java.util.List;

import me.Qball.Wild.Wild;

public class CheckConfig {
	Wild wild = Wild.getInstance();

	public boolean isCorrectWorld() {
		int wrong = 0;
		List<String> worlds = Wild.getWorlds();
		for (String s : worlds) {
			String[] world = s.split(":");
			if (world.length != 5) {
				wrong += 1;
				break;
			}
		}
		if (wrong == 1) {
			return false;
		}
		return true;
	}
	public boolean isCorrectPots() {
		int wrong = 0;
		List<String> pots = Wild.getListPots();
		for (String s : pots) {
			String[] pot = s.split(":");
			if (pot.length != 2) {
				wrong += 1;
				break;
			}
		}
		if (wrong == 1) {
			return false;
		}
		return true;
	}
} 