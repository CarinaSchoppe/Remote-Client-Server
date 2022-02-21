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

import me.carinasophie.server.Server
import me.carinasophie.util.FileHandler
import me.carinasophie.util.Messages
import me.carinasophie.util.Ranks
import me.carinasophie.util.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Logger
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class Minecraft : JavaPlugin() {

    companion object {
        var debug: Boolean = true
        lateinit var instance: Minecraft
        lateinit var prefix: String
        lateinit var fileHandler: FileHandler
        lateinit var server: Server
    }


    override fun onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("plugin-disabled")))
        fileHandler.saveConfigs()
    }

    override fun onEnable() {
        instance = this
        init(Bukkit.getPluginManager())
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("plugin-enabled")))
        val logger: Logger = LogManager.getRootLogger() as Logger

        val appender = me.carinasophie.server.minecraft.Logger()
        logger.addAppender(appender)

    }

    private fun init(pluginManager: PluginManager) {
        val messages = Messages().add()
        Messages.addMessagesDefault(messages.messagesConfig)
        fileHandler = FileHandler()
        User.addUserToConfig(fileHandler.ymlConfigUsers, User("PixelsDE", "pixelsde", Ranks.getRank("ADMIN")!!))
        User.registerUsers(fileHandler.ymlConfigUsers)
        Minecraft.server = Server(fileHandler.ymlConfigSettings.getInt("default-server-port"))

    }

}
