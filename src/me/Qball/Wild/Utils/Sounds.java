package me.Qball.Wild.Utils;


import java.util.HashMap;
import java.util.Map;

import me.Qball.Wild.Wild;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

public class Sounds {
	public static Sound sound;
	public static Plugin wild = Wild.getInstance();
	static Map<String,String> soundMap = new HashMap<String,String>();
	
	public static void init()
	{
		String[] tmp = Bukkit.getVersion().split("MC: ");
		String version =tmp[tmp.length-1].substring(0,3);
		if(version.equals("1.9") || version.equals("1.10"))
		{
			soundMap.put("Enderman_Teleport", "ENTITY_ENDERMEN_TELEPORT");
			soundMap.put("Egg_Pop", "ENTITY_CHICKEN_EGG");
			soundMap.put("Enderdragon_Growl", "ENITY_ENDERDRAGON_GROWL");
			soundMap.put("Enderman_Scream", "ENTITY_ENDERMEN_SCREAM");
			soundMap.put("Portal_Travel","BLOCK_PORTAL_TRAVEL");
			soundMap.put("Ghast_Moan", "ENTITY_GHAST_WARN");
			soundMap.put("Ghast_Scream", "ENTITY_GHAST_SCREAM");
			soundMap.put("Explode", "ENTITY_GENERIC_EXPLODE");		
			soundMap.put("No-Match", "AMBIENT_CAVE");
			soundMap.put("Arrow Hit", "ENTITY_ARROW_HIT");
		}
		else
		{
			soundMap.put("Enderman_Teleport","ENDERMAN_TELEPORT");
			soundMap.put("Egg_Pop", "CHICKEN_EGG_POP");
			soundMap.put("Enderdragon_Growl","ENDERDRAGON_GROWL");
			soundMap.put("Enderman_Scream", "ENDERMAN_SCREAM");
			soundMap.put("Portal_Travel","PORTAL_TRAVEL");
			soundMap.put("Ghast_Moan", "GHAST_MOAN");
			soundMap.put("Ghast_Scream","GHAST_SCREAM2");
			soundMap.put("Explode","EXPLODE");
			soundMap.put("No-Match","AMBIENCE_CAVE");
			soundMap.put("Arrow Hit", "ARROW_HIT");
		}
	}
	// Big thanks to Taliun of spigot for the idea on how to do sounds
public  static  Sound getSound()
{
	String sounds = wild.getConfig().getString("Sound");
	sounds = sounds.toLowerCase();
	try
	{
	switch(sounds)
	{
	case "enderman teleport":
		sound = Sound.valueOf(soundMap.get("Enderman_Teleport"));
		break;
	case "egg pop":
		sound = Sound.valueOf(soundMap.get("Egg_Pop"));
		break;
	case "dragon growl":
		sound = Sound.valueOf(soundMap.get("Enderdragon_Growl"));
		break;
	case "enderman scream":
		sound = Sound.valueOf(soundMap.get("Enderman_Scream"));
		break;
	case "portal travel":
		sound = Sound.valueOf(soundMap.get("Portal_Travel"));
		break;
	case "ghast moan":
		sound = Sound.valueOf(soundMap.get("Ghast_Moan"));
		break;
	case "ghast scream":
		sound = Sound.valueOf(soundMap.get("Ghast_Screan"));
		break;
	case "explosion":
		sound = Sound.valueOf(soundMap.get("Explode"));
		break;
	case "arrow hit":
		sound = Sound.valueOf(soundMap.get("Arrow Hit"));
		break;
	default:
		sounds = sounds.toUpperCase();
		sound = Sound.valueOf(sounds);
		break;
		
		
				
		
		
	}
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
		Bukkit.getLogger().info("Please report this");
	}
	return sound;

}

public static boolean Match()
{
	String Sounds = wild.getConfig().getString("Sound");
	String[] SoundDur = Sounds.split(":");
	String sounds = SoundDur[0];
	boolean Match = true;
	if(sounds.equalsIgnoreCase("enderman teleport")||
			sounds.equalsIgnoreCase("egg pop")||
			sounds.equalsIgnoreCase("dragon growl")||
			sounds.equalsIgnoreCase("enderman scream")||
			sounds.equalsIgnoreCase("portal travel")||
			sounds.equalsIgnoreCase("ghast moan")||
			sounds.equalsIgnoreCase("ghast scream")||
			sounds.equalsIgnoreCase("explosion")||
			sounds.equalsIgnoreCase("arrow hit"))
	{
		Match = true;
	}
	else
	{
		Match = false;
		
	}
	return Match;
}




}
