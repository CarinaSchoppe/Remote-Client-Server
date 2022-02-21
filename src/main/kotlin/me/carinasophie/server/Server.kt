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

import me.carinasophie.Minecraft
import me.carinasophie.util.Messages
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import me.carinasophie.util.User
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets

data class Client(val socket: Socket, var name: String?, val writer: PrintWriter, val reader: BufferedReader, var activated: Boolean = false, val user: User?)

class Server(port: Int) {

    val loginCode = "mc2912"
    private val serverSocket: ServerSocket
    private lateinit var reader: BufferedReader
    var writer: PrintWriter? = null
    val loggedInUsers: MutableList<User> = mutableListOf()

    init {
        serverSocket = ServerSocket(port)
        println(ChatColor.translateAlternateColorCodes('&', "&aServer started on port $port"))
        Thread {
            while (true) {
                val socket = serverSocket.accept()
                println(ChatColor.translateAlternateColorCodes('&', "&aA client connected"))
                reader = BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8), 16384)
                writer = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), 16384), true)
                val client = Client(socket, null, writer!!, reader, false, null)

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
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("client-disconnected").replace("%username%", client.name!!)))
                    return@Thread
                }
                if (input == null) {
                    client.socket.close()
                    loggedInUsers.remove(client.user)
                    break
                }
                val packet = Packet.fromJson(input)
                if (Minecraft.debug) Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("client-sent").replace("%username%", client.name ?: "unknown").replace("%message%", input)))
                if (!client.activated) {
                    if (packet.packetType == PacketType.LOGIN && packet.data.get("magic").asString.equals(loginCode)) {
                        for (user in loggedInUsers) {
                            if (user.username == packet.data.getAsJsonObject("login").get("username").asString) {
                                PacketMessageManager.doubleLogin(client)
                                return@Thread
                            }
                        }
                        for (user in User.users) {
                            if (user.username == packet.data.getAsJsonObject("login").get("username").asString && user.password == packet.data.getAsJsonObject("login").get("password").asString) {
                                client.activated = true
                                loggedInUsers.add(user)
                                client.name = packet.data.getAsJsonObject("login").get("username").asString
                                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.getMessage("client-activated").replace("%username%", client.name!!)))
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
                PacketMessageManager.packetHandler(client, packet)
            }
        }.start()
    }
}
