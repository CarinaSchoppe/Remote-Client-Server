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

class FileHandling(fileName: String) {
    var file: File
    var ymlConfig: YamlConfiguration

    private fun loadPrefix() {
        Minecraft.prefix = ymlConfig.getString("prefix")!!
    }

    init {
        file = File("$fileName.yml")
        ymlConfig = YamlConfiguration.loadConfiguration(file)
        saveDefauts()
        loadPrefix()
        Minecraft.debug = ymlConfig.getBoolean("debug")
    }

    fun addToConfig(key: String, value: Any) {
        ymlConfig.set(key, value)
        saveConfig()
    }


    private fun saveDefauts() {
        ymlConfig.addDefault("prefix", "&7[&b&lKotlinServerClient&7]")
        ymlConfig.addDefault("default-language", "en_US")
        ymlConfig.addDefault("default-server-port", "8080")
        ymlConfig.addDefault("debug", false)

        ymlConfig.options().copyDefaults(true)
        saveConfig()
    }

    private fun saveConfig() {
        try {
            ymlConfig.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
