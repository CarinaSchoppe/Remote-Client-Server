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
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration

class User(val username: String, val password: String, var rank: Ranks) {
    override fun toString(): String {
        return "User(username='$username':password='$password')"
    }

    fun hasPermission(command: String): Boolean {
        return rank.commands.contains(command)
    }


    companion object {
        fun addUserToConfig(config: YamlConfiguration, user: User) {
            val users = config.getStringList("users")
            if (!users.contains(user.username)) users.add(user.username)
            config.set("users", users)
            config.set("${user.username}.username", user.username)
            config.set("${user.username}.password", user.password)
            config.set("${user.username}.rank", user.rank.toString())
            Minecraft.fileHandler.saveConfigs()
        }

        lateinit var users: MutableList<User>
        fun registerUsers(config: YamlConfiguration) {
            users = mutableListOf()
            for (user in config.getList("users")!!) {
                val userConfig = config.getConfigurationSection("$user")!!
                val userCommands = mutableListOf<String>()
                for (rank in Ranks.ranks) {
                    if (userConfig.getString("rank")!! == rank.rankName) {
                        userCommands.addAll(rank.commands)
                        break
                    }
                }
                val client = User(userConfig.getString("username")!!, userConfig.getString("password")!!, Ranks.ranks.first { userConfig.getString("rank")!! == it.rankName })
                println(ChatColor.translateAlternateColorCodes('&', "&7[&a+&7] &aUser &7[&a+&7] &a${client.username} &7[&a+&7] &ahas been registered with rank &7[&a+&7] &a${client.rank.rankName}"))
                users.add(client)
            }
        }

        fun userExists(username: String, password: String): Boolean {
            for (user in users) {
                if (user.username == username && user.password == password) {
                    return true
                }
            }
            return false
        }
    }


}
