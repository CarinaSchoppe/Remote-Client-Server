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

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.carinasophie.Minecraft
import me.carinasophie.server.minecraft.Coordinates
import me.carinasophie.server.minecraft.Player
import me.carinasophie.util.Messages
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import org.bukkit.Bukkit
import org.bukkit.ChatColor

object PacketMessageManager {


    fun loginInvalid(client: Client) {
        val json = JsonObject()
        json.addProperty("action", "disconnect")
        json.addProperty("message", "Logindata invalid!")
        client.writer.println(Packet(PacketType.ERROR, json).createJsonPacket())
        disconnect(client)

    }

    fun packetHandler(client: Client, packet: Packet) {
        if (packet.packetType == PacketType.COMMAND) {
            val command = packet.data.get("command").asString
            if (command.startsWith("/")) {
                if (!(client.user!!.hasPermission(command.substring(1).split(" ")[0]))) {
                    val json = JsonObject()
                    val type = JsonObject()
                    type.addProperty("text", "Not the permissions!")
                    type.addProperty("type", "fail")
                    json.add("info", type)
                    client.writer.println(Packet(PacketType.INFO, json).createJsonPacket())
                }
                Minecraft.instance.server.dispatchCommand(Minecraft.instance.server.consoleSender, command.substring(1))
                val json = JsonObject()
                val type = JsonObject()
                type.addProperty("text", "Command executed!")
                type.addProperty("type", "success")
                json.add("info", type)
                client.writer.println(Packet(PacketType.INFO, json).createJsonPacket())
            }
        } else if (packet.packetType == PacketType.REFRESH) {
            refreshPlayers(client)
        }
    }

    fun refreshPlayers(client: Client) {
        val json = JsonObject()
        json.addProperty("action", "refreshPlayers")
        val players = mutableListOf<Player>()
        for (player in Minecraft.instance.server.onlinePlayers) {
            val p = Player(player.name, player.health.toInt(), player.foodLevel, Coordinates(player.location.x.toInt(), player.location.y.toInt(), player.location.z.toInt()), player.world.name)
            players.add(p)
        }
        json.add("players", Gson().toJsonTree(players))
        println(json)
        client.writer.println(Packet(PacketType.REFRESH, json).createJsonPacket())
    }

    fun disconnect(client: Client) {
        Minecraft.server.loggedInUsers.remove(client.user)
        client.socket.close()
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("client-disconnected").replace("%username%", client.name ?: "unknown")))
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
