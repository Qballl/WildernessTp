package me.qball.wild.util

import me.qball.wild.Wild

object CheckConfig {

    private val plugin = Wild.instance

    fun isCorrectPots() : Boolean {
        var wrong = 0
        val pots = plugin.getListPots()
        for(i in 0..pots.size) {
            val pot = pots.get(i).split(":")
            if(pot.size != 2) {
                wrong++
                break
            }
        }
        return wrong == 0
    }
}


