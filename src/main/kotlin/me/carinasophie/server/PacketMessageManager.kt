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
        when (packet.packetType) {
            PacketType.CHAT -> {
                recieveChat(client, packet)
            }
            PacketType.COMMAND -> {
                val command = packet.data.get("command").asString
                if (command.startsWith("/")) {
                    println("iojespfoijsf: " + client.user)
                    if (!(client.user!!.hasPermission(command.substring(1).split(" ")[0]))) {
                        val json = JsonObject()
                        val type = JsonObject()
                        type.addProperty("text", "Not the permissions!")
                        type.addProperty("type", "fail")
                        json.add("info", type)
                        client.writer.println(Packet(PacketType.INFO, json).createJsonPacket())
                    }
                    Bukkit.getScheduler().runTask(Minecraft.instance, Runnable {
                        Minecraft.instance.server.dispatchCommand(Minecraft.instance.server.consoleSender, command.substring(1))
                    })
                    sendSuccess(client)
                } else {
                    messageBroadcaster(command, client)
                }

            }
            PacketType.REFRESH -> {
                refreshPlayers(client, Bukkit.getOnlinePlayers().toTypedArray())
            }
            PacketType.LOGOUT -> {
                disconnect(client)
            }
        }
    }

    private fun messageBroadcaster(message: String, client: Client) {
        val message = ChatColor.translateAlternateColorCodes('&', Messages.getMessage("admin-message").replace("%message%", message)) + ""
        Bukkit.getConsoleSender().sendMessage(message)
        for (player in Bukkit.getOnlinePlayers()) {
            player.sendMessage(message)
        }
        sendSuccess(client)
    }

    private fun sendSuccess(client: Client) {
        val json = JsonObject()
        val type = JsonObject()
        type.addProperty("text", "Command was successfully executed!")
        type.addProperty("type", "success")
        type.addProperty("title", "Command executed!")
        json.add("info", type)
        client.writer.println(Packet(PacketType.INFO, json).createJsonPacket())
    }

    fun refreshPlayers(client: Client, playersBukkit: Array<org.bukkit.entity.Player>) {
        val json = JsonObject()
        json.addProperty("action", "refreshPlayers")
        val players = mutableListOf<Player>()
        for (player in playersBukkit) {
            val p = Player(player.name, player.health.toInt(), player.foodLevel, Coordinates(player.location.x.toInt(), player.location.y.toInt(), player.location.z.toInt()), player.world.name, player.gameMode.name, level = player.level, ping = player.ping)
            players.add(p)
        }
        json.add("players", Gson().toJsonTree(players))
        println(json)
        client.writer.println(Packet(PacketType.REFRESH, json).createJsonPacket())
    }

    fun disconnect(client: Client) {
        val json = JsonObject()
        Minecraft.server.loggedInClients.remove(client)
        client.writer.println(Packet(PacketType.LOGOUT, json).createJsonPacket())
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

    fun recieveChat(client: Client, packet: Packet) {
        val chat = packet.data.get("chat").asString
        val json = JsonObject()
        json.addProperty("player", client.name)
        json.addProperty("message", chat)
        for (client in Minecraft.server.loggedInClients) client.writer.println(Packet(PacketType.CHAT, json).createJsonPacket())
        messageBroadcaster(chat, client)
    }
}
