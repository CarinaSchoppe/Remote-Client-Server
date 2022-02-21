/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 00:53 by Carina The Latest changes made by Carina on 21.02.22, 00:53.
 *  All contents of "PacketMessageManager.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.server

import com.google.gson.JsonObject
import me.carinasophie.Minecraft
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import org.bukkit.ChatColor

object PacketMessageManager {


    fun loginInvalid(client: Client) {
        val json = JsonObject()
        json.addProperty("action", "disconnect")
        json.addProperty("message", "Logindata invalid!")
        client.writer.println(Packet(PacketType.ERROR, json).createJsonPacket())
        disconnect(client)

    }

    fun disconnect(client: Client) {
        client.socket.close()
        Minecraft.server.users.remove(client.user)
        println(ChatColor.translateAlternateColorCodes('&', "&cClient disconnected"))
    }

    fun doubleLogin(client: Client) {
        val json = JsonObject()
        json.addProperty("action", "disconnect")
        json.addProperty("message", "Allready logged-in!")
        println(Packet(PacketType.ERROR, json).createJsonPacket())
        client.writer.println(Packet(PacketType.ERROR, json).createJsonPacket())
        disconnect(client)
    }

    fun loginSuccess(client: Client) {
        val json = JsonObject()
        json.addProperty("message", "Logindata valid!")
        client.writer.println(Packet(PacketType.SUCCESS, json).createJsonPacket())
    }

    fun sendLoginToClient(client: Client) {
        val json = JsonObject()
        json.addProperty("magic", Minecraft.server.loginCode)
        client.writer.println(Packet(PacketType.LOGIN, json).createJsonPacket())
    }
}
