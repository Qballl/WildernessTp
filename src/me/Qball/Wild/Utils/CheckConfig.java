package me.Qball.Wild.Utils;

import java.util.List;

import me.Qball.Wild.Wild;

public class CheckConfig {
	Wild wild = Wild.getInstance();


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