/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "Messages.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import me.carinasophie.Minecraft
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class Messages {
    private val messagesFile: File = File("plugins/RemoteServerGUI/messages.yml")
    var messagesConfig: YamlConfiguration = YamlConfiguration.loadConfiguration(messagesFile)

    fun add(): Messages {
        messageInstance = this
        return this
    }

    companion object {
        lateinit var messageInstance: Messages

        fun addMessagesDefault(config: YamlConfiguration) {
            config.addDefault("register-user", "&7[&a+&7] &aUser &7[&a+&7] &a%username% &7[&a+&7] &ahas been registered with rank &7[&a+&7] &a%rank%")
            config.addDefault("rank-exists", "&cRank &6%rank% &calready exists!")
            config.addDefault("rank-load", "&aRank &6%rank% &awith commands: &6%commands% &aloaded!")
            config.addDefault("server-started", "&aServer started on port &6%port%&a!")
            config.addDefault("client-connected", "&aA client connected!")
            config.addDefault("client-disconnected", "&cClient &6%username% &cdisconnected!")
            config.addDefault("client-sent", "&7Client &6%username% sent: &6%message%&7!")
            config.addDefault("client-activated", "&aClient activated: &6%username%!")
            config.addDefault("plugin-enabled", "&7>> &c&lEnabled!")
            config.addDefault("plugin-disabled", "&7>> &c&lDisabled!")
            config.addDefault("admin-message", "&6Admin-System-Broadcast&7>> &d%message%")
            config.options().copyDefaults(true)
            config.save(messageInstance.messagesFile)
        }

        fun getMessage(key: String): String {
            return (Minecraft.prefix + messageInstance.messagesConfig.getString(key))
        }
    }
}
