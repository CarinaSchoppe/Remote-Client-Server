/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 18:40 by Carina The Latest changes made by Carina on 20.02.22, 18:40.
 *  All contents of "User.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import me.carinasophie.Minecraft
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration

class User(val username: String, val password: String, val rank: Ranks) {
    override fun toString(): String {
        return "User(username='$username':password='$password')"
    }

    fun hasPermission(command: String): Boolean {
        return rank.commands.contains(command)
    }


    companion object {
        val users = mutableListOf<User>()

        fun addUserToConfig(config: YamlConfiguration, user: User) {
            config.set("${user.username}.username", user.username)
            config.set("${user.username}.password", user.password)
            config.set("${user.username}.rank", user.rank.rankName)
            Minecraft.fileHandler.saveConfigs()
        }

        fun registerUsers(config: YamlConfiguration) {
            for (key in config.getKeys(false)) {
                val client = User(config.getString("$key.username")!!, config.getString("$key.password")!!, Ranks.ranks.first { config.getString("$key.rank")!! == it.rankName })
                users.add(client)
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("register-user").replace("%username%", client.username).replace("%rank%", client.rank.rankName)))
            }
        }


    }


}
