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

class FileHandling {
    private val fileSettings: File = File("plugins/RemoteServerGUI/settings.yml")
    val ymlConfigSettings: YamlConfiguration
    private val fileMessages: File
    val ymlConfigMessages: YamlConfiguration
    private val fileUsers: File
    val ymlConfigUsers: YamlConfiguration
    private val fileRanks: File
    val ymlConfigRanks: YamlConfiguration

    private fun loadPrefix() {
        Minecraft.prefix = ymlConfigSettings.getString("prefix")!!
    }

    init {
        fileMessages = File("plugins/RemoteServerGUI/messages.yml")
        fileUsers = File("plugins/RemoteServerGUI/users.yml")
        fileRanks = File("plugins/RemoteServerGUI/ranks.yml")
        ymlConfigSettings = YamlConfiguration.loadConfiguration(fileSettings)
        ymlConfigRanks = YamlConfiguration.loadConfiguration(fileRanks)
        ymlConfigMessages = YamlConfiguration.loadConfiguration(fileMessages)
        ymlConfigUsers = YamlConfiguration.loadConfiguration(fileUsers)
        saveDefauts()
        loadPrefix()
        Minecraft.debug = ymlConfigSettings.getBoolean("debug")
        Ranks.loadRanks(ymlConfigRanks)
    }

    fun addToConfig(key: String, value: Any) {
        ymlConfigSettings.set(key, value)
        saveConfigs()
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
