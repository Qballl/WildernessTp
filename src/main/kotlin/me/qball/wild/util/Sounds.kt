package me.qball.wild.util

import me.qball.wild.Wild
import org.bukkit.Sound
import java.util.HashMap

object Sounds {
    private lateinit var sound: Sound
    private val plugin = Wild.instance
    private val soundMap = HashMap<String, String>()

    fun init() {
        val version = plugin.server.javaClass.`package`.name.split(".")[3]
        if(version.contains("1_9") || version.contains("1_10")) {
            soundMap.put("Enderman_Teleport", "ENTITY_ENDERMEN_TELEPORT")
            soundMap.put("Egg_Pop", "ENTITY_CHICKEN_EGG")
            soundMap.put("Enderdragon_Growl", "ENITY_ENDERDRAGON_GROWL")
            soundMap.put("Enderman_Scream", "ENTITY_ENDERMEN_SCREAM")
            soundMap.put("Portal_Travel", "BLOCK_PORTAL_TRAVEL")
            soundMap.put("Ghast_Moan", "ENTITY_GHAST_WARN")
            soundMap.put("Ghast_Scream", "ENTITY_GHAST_SCREAM")
            soundMap.put("Explode", "ENTITY_GENERIC_EXPLODE")
            soundMap.put("No-Match", "AMBIENT_CAVE")
            soundMap.put("Arrow Hit", "ENTITY_ARROW_HIT")
        }else {
            soundMap.put("Enderman_Teleport", "ENDERMAN_TELEPORT")
            soundMap.put("Egg_Pop", "CHICKEN_EGG_POP")
            soundMap.put("Enderdragon_Growl", "ENDERDRAGON_GROWL")
            soundMap.put("Enderman_Scream", "ENDERMAN_SCREAM")
            soundMap.put("Portal_Travel", "PORTAL_TRAVEL")
            soundMap.put("Ghast_Moan", "GHAST_MOAN")
            soundMap.put("Ghast_Scream", "GHAST_SCREAM2")
            soundMap.put("Explode", "EXPLODE")
            soundMap.put("No-Match", "AMBIENCE_CAVE")
            soundMap.put("Arrow Hit", "ARROW_HIT")
        }
    }

    fun getSound() : Sound {
        val sound = plugin.config.getString("Sound").toLowerCase()
        try {
            when(sound) {
                "enderman teleport" -> return Sound.valueOf(soundMap["Enderman_Teleport"]!!)
                "egg pop" -> return Sound.valueOf(soundMap["Egg_Pop"]!!)
                "dragon growl" -> return Sound.valueOf(soundMap["Enderdragon_Growl"]!!)
                "enderman scream" -> return Sound.valueOf(soundMap["Enderman_Scream"]!!)
                "portal travel" -> return Sound.valueOf(soundMap["Portal_Travel"]!!)
                "ghast moan" -> return Sound.valueOf(soundMap["Ghast_Moan"]!!)
                "ghast scream" -> return Sound.valueOf(soundMap["Ghast_Scream"]!!)
                "explosion" -> return Sound.valueOf(soundMap["Explode"]!!)
                "arrow hit" -> return Sound.valueOf(soundMap["Arrow Hit"]!!)
                else -> return Sound.valueOf(soundMap["No-Match"]!!)
            }
        }catch(e: NullPointerException) {
            plugin.logger.severe("Something went wrong! report this to qball!")
            return Sound.valueOf(soundMap["No-Match"]!!)
        }
    }

    fun match() : Boolean {

        val sounds = plugin.config.getString("Sound").split(":")[0]
        return sounds.equals("enderman teleport", true) ||
                sounds.equals("egg pop", true) ||
                sounds.equals("dragon growl", true) ||
                sounds.equals("enderman scream", true) ||
                sounds.equals("portal travel", true) ||
                sounds.equals("ghast moan", true) ||
                sounds.equals("ghast scream", true) ||
                sounds.equals("explosion", true) ||
                sounds.equals("arrow hit", true)
    }
}


