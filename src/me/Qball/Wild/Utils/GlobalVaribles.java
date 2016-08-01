package me.Qball.Wild.Utils;

import me.Qball.Wild.Wild;

public class GlobalVaribles {
	public static void setAll()
	{
		Wild wild = Wild.getInstance();
		wild.cost = wild.getConfig().getInt("Cost");
		wild.costMSG = wild.getConfig().getString("Costmsg");
		wild.strCost = String.valueOf(wild.cost);
		wild.costMsg =  wild.costMSG.replaceAll("\\{cost\\}", wild.strCost);
	}
}
