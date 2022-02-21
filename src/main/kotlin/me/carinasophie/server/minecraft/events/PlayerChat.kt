/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 15:34 by Carina The Latest changes made by Carina on 21.02.22, 15:34.
 *  All contents of "PlayerChat.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.server.minecraft.events

import com.google.gson.JsonObject
import io.papermc.paper.event.player.AsyncChatEvent
import me.carinasophie.Minecraft
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerChat : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncChatEvent) {
        val json = JsonObject()
        json.addProperty("player", event.player.name)
        json.addProperty("message", PlainTextComponentSerializer.plainText().serialize(event.message()))
        for (client in Minecraft.server.loggedInClients)
            client.writer.println(Packet(PacketType.CHAT, json).createJsonPacket())

    }
}
