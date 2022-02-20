/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "Minecraft.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie

import me.carinasophie.util.FileHandling
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class Minecraft : JavaPlugin() {

    companion object {
        var debug: Boolean = false
        lateinit var instance: Minecraft
        lateinit var prefix: String
        lateinit var fileHandler: FileHandling
    }


    override fun onDisable() {
        println("${ChatColor.translateAlternateColorCodes('&', "&c&lKotlinServerMC &7>> &c&lDisabled")}")
    }

    override fun onEnable() {
        instance = this
        init(Bukkit.getPluginManager())
        println("${ChatColor.translateAlternateColorCodes('&', "&c&lKotlinServerMC &7>> &c&lEnabled")}")

    }

    fun init(pluginManager: PluginManager) {
        fileHandler = FileHandling("config")

    }

}
