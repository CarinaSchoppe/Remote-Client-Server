/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 18:39 by Carina The Latest changes made by Carina on 20.02.22, 18:39.
 *  All contents of "Server.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
import me.carinasophie.util.User
import org.bukkit.ChatColor
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets

data class Client(val socket: Socket, var name: String?, val writer: PrintWriter, val reader: BufferedReader, var activated: Boolean = false, val user: User?)

class Server(port: Int) {

    val loginCode = "mc2912"
    private val serverSocket: ServerSocket
    lateinit var reader: BufferedReader
    lateinit var writer: PrintWriter
    val clients: MutableList<User> = mutableListOf()

    init {
        serverSocket = ServerSocket(port)
        println(ChatColor.translateAlternateColorCodes('&', "&aServer started on port $port"))
        Thread {
            while (true) {
                val socket = serverSocket.accept()
                println(ChatColor.translateAlternateColorCodes('&', "&aA client connected"))
                reader = BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8), 16384)
                writer = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), 16384), true)
                val client = Client(socket, null, writer, reader, false, null)

                PacketMessageManager.sendLoginToClient(client)
                readInput(client)
            }
        }.start()
    }


    private fun readInput(client: Client) {
        Thread {
            while (true) {
                var input: String?
                try {
                    input = client.reader.readLine()
                } catch (e: Exception) {
                    client.socket.close()
                    println(ChatColor.translateAlternateColorCodes('&', "&cClient disconnected"))
                    return@Thread
                }
                if (input == null) {
                    client.socket.close()
                    clients.remove(client.user)
                    break
                }
                val packet = Packet.fromJson(input)
                if (Minecraft.debug) println(ChatColor.translateAlternateColorCodes('&', "&aClient sent: $input"))
                if (!client.activated) {
                    if (packet.packetType == PacketType.LOGIN && packet.data.get("magic").asString.equals(loginCode)) {
                        for (user in User.users) {
                            if (user in clients) {
                                PacketMessageManager.doubleLogin(client)
                                return@Thread
                            }
                            if (user.username == packet.data.getAsJsonObject("login").get("username").asString && user.password == packet.data.getAsJsonObject("login").get("password").asString) {
                                client.activated = true
                                clients.add(user)
                                client.name = packet.data.getAsJsonObject("login").get("username").asString
                                println(ChatColor.translateAlternateColorCodes('&', "&aClient activated: ${client.name}"))
                                PacketMessageManager.loginSuccess(client)
                                break
                            }
                        }
                        if (!client.activated) {
                            PacketMessageManager.loginInvalid(client)
                            return@Thread
                        }
                    } else {
                        PacketMessageManager.loginInvalid(client)
                        return@Thread
                    }
                }
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
                }
            }
        }.start()
    }
}
