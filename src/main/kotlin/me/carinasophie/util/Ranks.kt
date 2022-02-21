/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 18:39 by Carina The Latest changes made by Carina on 20.02.22, 16:44.
 *  All contents of "Ranks.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration

data class Ranks(val rankName: String, val commands: MutableList<String>) {

    companion object {
        fun loadRanks(ymlConfig: YamlConfiguration) {
            for (key in ymlConfig.getKeys(false)) {
                val commands = ymlConfig.getStringList(key)
                if (!containsRank(key)) {
                    ranks.add(Ranks(key, commands))
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("rank-load").replace("%rank%", key).replace("%commands%", commands.toString())))
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("rank-exists").replace("%rank%", key)))
                }
            }
        }

        val ranks = mutableListOf<Ranks>()
        fun getRank(code: String): Ranks? {
            return ranks.find { it.rankName == code }
        }

        private fun containsRank(code: String): Boolean {
            return ranks.find { it.rankName == code } != null
        }
    }


}
