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
    private val fileSettings: File
    val ymlConfigSettings: YamlConfiguration
    private val fileMessages: File
    val ymlConfigMessages: YamlConfiguration
    private val fileUsers: File
    val ymlConfigUsers: YamlConfiguration

    private fun loadPrefix() {
        Minecraft.prefix = ymlConfigSettings.getString("prefix")!!
    }

    init {
        fileSettings = File("plugins/RemoteServerGUI/settings.yml")
        fileMessages = File("plugins/RemoteServerGUI/messages.yml")
        fileUsers = File("plugins/RemoteServerGUI/users.yml")
        ymlConfigSettings = YamlConfiguration.loadConfiguration(fileSettings)
        ymlConfigMessages = YamlConfiguration.loadConfiguration(fileMessages)
        ymlConfigUsers = YamlConfiguration.loadConfiguration(fileUsers)
        saveDefauts()
        loadPrefix()
        Minecraft.debug = ymlConfigSettings.getBoolean("debug")
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
        ymlConfigSettings.options().copyDefaults(true)
        saveConfigs()
    }

    fun saveConfigs() {
        try {
            ymlConfigUsers.save(fileUsers)
            ymlConfigSettings.save(fileSettings)
            ymlConfigMessages.save(fileMessages)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
