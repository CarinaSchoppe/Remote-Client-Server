/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 11:00 by Carina The Latest changes made by Carina on 21.02.22, 11:00.
 *  All contents of "Logger.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.server.minecraft

import com.google.gson.JsonObject
import me.carinasophie.Minecraft
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender


class Logger : AbstractAppender {

    companion object {
        var console = """"""
        var chat = """"""
    }

    constructor() : super("KotlinServerClientGUILogger", null, null)

    init {
        start()
    }

    override fun append(event: LogEvent) {
        val log = event.toImmutable()
        val json = JsonObject()
        json.addProperty("log", log.message.formattedMessage)
        console += log.message.formattedMessage + "\n"
        for (client in Minecraft.server.loggedInClients) {
            client.writer.println(Packet(PacketType.LOG, json).createJsonPacket())
        }
    }
}
