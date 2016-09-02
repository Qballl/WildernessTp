package me.qball.wild.util

import me.qball.wild.Wild

object LoadDependencies {
    private val plugin = Wild.instance

    fun loadAll() {
        loadTowny()
        loadFactions()
        loadGriefPrevention()
        loadWorldGuard()
        loadKingdoms()
    }

    private fun loadTowny() {
        if(plugin.config.getBoolean("Towny")) {
            if(!plugin.server.pluginManager.isPluginEnabled("Towny")) {
                plugin.server.pluginManager.disablePlugin(plugin)
            }else {
                plugin.logger.info("Towny hook enable")
            }
        }
    }

    private fun loadFactions() {
        if(plugin.config.getBoolean("Factions")) {
            if(!plugin.server.pluginManager.isPluginEnabled("Factions")) {
                plugin.server.pluginManager.disablePlugin(plugin)
            }else {
                plugin.logger.info("Factions hook enabled")
            }
        }
    }

    private fun loadGriefPrevention() {
        if(plugin.config.getBoolean("GriefPrevention")) {
            if(!plugin.server.pluginManager.isPluginEnabled("GriefPrevention")) {
                plugin.server.pluginManager.disablePlugin(plugin)
            }else {
                plugin.logger.info("GriefPrevention hook enabled")
            }
        }
    }

    private fun loadWorldGuard() {
        if(plugin.config.getBoolean("WorldGuard")) {
            if(!plugin.server.pluginManager.isPluginEnabled("WorldGuard")) {
                plugin.server.pluginManager.disablePlugin(plugin)
            }else {
                plugin.logger.info("WorldGuard hook enabled")
            }
        }
    }

    private fun loadKingdoms() {
        if(plugin.config.getBoolean("Kingdoms")) {
            if(!plugin.server.pluginManager.isPluginEnabled("Kingdoms")) {
                plugin.server.pluginManager.disablePlugin(plugin)
            }else {
                plugin.logger.info("Kingdoms hook enabled")
            }
        }
    }
}


