/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "FileHandling.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import me.carinasophie.Minecraft
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class FileHandler {
    private val fileSettings: File = File("plugins/RemoteServerGUI/settings.yml")
    val ymlConfigSettings: YamlConfiguration = YamlConfiguration.loadConfiguration(fileSettings)
    private val fileMessages: File = File("plugins/RemoteServerGUI/messages.yml")
    private val ymlConfigMessages: YamlConfiguration = YamlConfiguration.loadConfiguration(fileMessages)
    private val fileUsers: File = File("plugins/RemoteServerGUI/users.yml")
    val ymlConfigUsers: YamlConfiguration = YamlConfiguration.loadConfiguration(fileUsers)
    private val fileRanks: File = File("plugins/RemoteServerGUI/ranks.yml")
    private val ymlConfigRanks: YamlConfiguration = YamlConfiguration.loadConfiguration(fileRanks)

    init {
        saveDefauts()
        Minecraft.prefix = ymlConfigSettings.getString("prefix")!! + " "
        Minecraft.debug = ymlConfigSettings.getBoolean("debug")
        Ranks.loadRanks(ymlConfigRanks)
    }


    private fun saveDefauts() {
        ymlConfigSettings.addDefault("prefix", "&7[&b&lKotlinServerClient&7]")
        ymlConfigSettings.addDefault("default-language", "en_US")
        ymlConfigSettings.addDefault("default-server-port", 8080)
        ymlConfigSettings.addDefault("debug", true)
        ymlConfigRanks.addDefault("ADMIN", arrayOf("ban", "kick", "mute", "say", "unmute", "unban", "msg", "sudo"))
        ymlConfigRanks.addDefault("MODERATOR", arrayOf("kick", "mute", "say", "unmute", "msg"))
        ymlConfigSettings.options().copyDefaults(true)
        ymlConfigRanks.options().copyDefaults(true)
        saveConfigs()
    }

    fun saveConfigs() {
        try {
            ymlConfigUsers.save(fileUsers)
            ymlConfigRanks.save(fileRanks)
            ymlConfigSettings.save(fileSettings)
            ymlConfigMessages.save(fileMessages)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
