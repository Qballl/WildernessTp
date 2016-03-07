package me.Qball.Wild;


import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.Listener; 
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Sounds extends JavaPlugin implements Listener{
	public static Sound sound;
	public static Plugin Wild;
public  static  Sound getSound()
{
	Bukkit.getPluginManager().registerEvents((Listener) Wild, (Plugin) Wild);
	String sounds = Wild.getConfig().getString("Sound");
	sounds = sounds.toLowerCase();
	switch(sounds)
	{
	case "enderman teleport":
		sound = Sound.ENDERMAN_TELEPORT;
		break;
	case "egg pop":
		sound = Sound.CHICKEN_EGG_POP;
		break;
	case "dragon growl":
		sound = Sound.ENDERDRAGON_GROWL;
		break;
	case "enderman scream":
		sound = Sound.ENDERMAN_SCREAM;
		break;
	case "portal travel":
		sound = Sound.PORTAL_TRAVEL;
		break;
	case "ghast moan":
		sound = Sound.GHAST_MOAN;
		break;
	case "ghast scream":
		sound = Sound.GHAST_SCREAM;
		break;
	case "explosion":
		sound = Sound.EXPLODE;
		break;
	default:
		sound = Sound.CLICK;
		throw new IllegalArgumentException("Error cannot find spefied sound. Please check config");
		
		
		
	}
	return sound;

}



}
