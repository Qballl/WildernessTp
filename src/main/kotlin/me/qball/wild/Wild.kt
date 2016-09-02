package me.qball.wild

import me.qball.wild.listeners.PlayerMoveListener
import me.qball.wild.util.CheckConfig
import me.qball.wild.util.Checks
import me.qball.wild.util.ClaimChecks
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.ArrayList
import java.util.HashMap
import java.util.UUID
import java.util.concurrent.TimeUnit


class Wild : JavaPlugin(), Listener {

    val cooldownTime = HashMap<UUID, Long>()
    val cooldownCheck = HashMap<UUID, Int>()
    var rem: Int? = null
    var cost = config.getInt("Cost")
    var costMsg = config.getString("Costmsg").replace("{cost}", cost.toString())
    var retries = config.getInt("Retries")
    lateinit var econ: Economy
    val moveListener = PlayerMoveListener(this)

    companion object {
        val instance = Wild()
    }
    override fun onEnable() {
        saveDefaultConfig()
        saveResource("PotionsEffects.txt", true)
        saveResource("Biomes.txt", true)
        saveResource("Sounds.txt", true)

        val (economyDone, err) = setupEconomy()
        if(!economyDone) {
            logger.severe(err)
            server.pluginManager.disablePlugin(this)
        }
    }

    private fun setupEconomy() : Pair<Boolean, String>{
        if(!server.pluginManager.isPluginEnabled("Vault")) {
            return Pair(false, "Disabling due to no Vault dependency found")
        }
        val rsp = server.servicesManager.getRegistration(Economy::class.java) ?: return Pair(false, "Disabling due to no economy provider found")
        econ = rsp.provider
        return Pair(true, "")
    }

    fun getListPots() : ArrayList<String> {
        return config.getStringList("Potions") as ArrayList<String>
    }

    override fun onCommand(p: CommandSender, cmd: Command, label: String, args: Array<String>) : Boolean {
        val cd = config.getInt("Cooldown")
        val coolMsg = config.getString("Cooldownmsg").replace("{cool}", cd.toString())

        if(cmd.name.equals("wild", true)) {
            if(p is Player) {

            }
        }
        return false
    }

    fun getRem(p: Player): Int {
        var rem = 0
        if (cooldownCheck.containsKey(p.uniqueId)) {
            rem = cooldownCheck[p.uniqueId] ?: return -1
        }
        return rem
    }

    fun check(p: Player) : Boolean {
        val cool = config.getInt("Cooldown")

        if (cooldownTime.containsKey(p.uniqueId)) {
            val old = cooldownTime[p.uniqueId] ?: return false
            val now = System.currentTimeMillis()

            val diff = now - old

            val convert = TimeUnit.MILLISECONDS.toSeconds(diff)
            val Rem = cool + convert.toInt()
            if (convert >= cool) {
                cooldownTime.put(p.uniqueId, now)
                try {
                    cooldownCheck.remove(p.uniqueId)
                } catch (e: NullPointerException) {
                }

                return true
            }
            cooldownCheck.put(p.uniqueId, Rem)
            return false
        } else {
            cooldownTime.put(p.uniqueId, System.currentTimeMillis())
            try {
                cooldownCheck.remove(p.uniqueId)
            } catch (e: NullPointerException) {
            }

            return true
        }
    }

    fun reload(p: Player) {
        Bukkit.getServer().pluginManager.getPlugin("Wild").reloadConfig()
        if (!CheckConfig.isCorrectPots()) {
            Bukkit.getLogger()pask oc;;;;;;;;a.info("Config for potions is misconfigured please check the documentation on the plugin page to make sure you have configured correctly")
            Bukkit.getLogger()pask oc;;;;;;;;a.info("Plugin will now disable")
            Bukkit.getPluginMapask oc;;;;;;;;anager().disablePlugin(this)
        } else {pask oc;;;;;;;;a
            p.sendMessage("${ChatColor.BLACK}[${ChatColor.GREEN}WildernessTP${ChatColor.BLACK}]${ChatColor.GREEN}Pluign config has been successfully reloaded")
        }
    }

    fun random(e: Player, location: Location) {
        val target = e
        val random = GetRandomLocation()
        val Message = plugin.getConfig().gepask oc;;;;;;;;atString("No Suitable Location")
        val x = location.blockXpask oc;;;;;;;;a
        val z = location.blockZpask oc;;;;;;;;a
        val tele = TeleportTar()pask oc;;;;;;;;a
pask oc;;;;;;;;a
        if (Checks.inNether(x, z, target) === true) {
            val y = GetHighestNether.getSoildBlock(x, z, target)

            val done = Location(target.world, x + .5, y.toDouble(), z + .5, 0.0f, 0.0f)

            tele.TP(done, target)
        } else {
            val loc = Location(location.world, location.blockX + .5, location.blockY.toDouble(), location.blockZ + .5, 0.0f, 0.0f)
            if (Checks.getLiquid(loc)
                    || claims.townyClaim(loc)
                    || claims.factionsClaim(loc)
                    || claims.greifPrevnClaim(loc)
                    || claims.worldGuardClaim(loc)) {

                if (config.getBoolean("Retry")) {
                    for (i in retries downTo 0) {
                        val info = random.getWorldInfomation(target)
                        val temp = random.getRandomLoc(info, target)
                        val test = Location(temp.getWorld(), temp.getBlockX() + .5, temp.getBlockY().toDouble(), temp.getBlockZ() + .5, 0.0f, 0.0f)
                        if (!Checks.getLiquid(test)
                                && !claims.townyClaim(test)
                                && !claims.factionsClaim(test)
                                && !claims.greifPrevnClaim(test)
                                && !claims.worldGuardClaim(test)
                                && !claims.kingdomClaimCheck(test)) {
                            if (getConfig().getBoolean("Play") == false) {
                                tele.TP(test, target)
                            } else {
                                tele.TP(test, target)

                            }
                            break
                        }
                        if (i == 0) {
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&'.toChar(),
                                    Message))
                            cooldownTime.remove(e.player.uniqueId)
                            cooldownCheck.remove(e.player.uniqueId)
                        }
                    }
                } else {
                    target.sendMessage(ChatColor.translateAlternateColorCodes(
                            '&'.toChar(), Message))
                    cooldownTime.remove(e.player.uniqueId)
                    cooldownCheck.remove(e.player.uniqueId)
                }

            } else {


                Checks.loadChunkIfNotLoaded(target)
                val loco = Location(location.world, location.blockX + .5, location.blockY.toDouble(), location.blockZ + .5, 0.0f, 0.0f)
                tele.TP(loco, target)

            }
        }
    }
}
