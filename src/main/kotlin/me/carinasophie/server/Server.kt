/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
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
import org.bukkit.ChatColor
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets

data class Client(val socket: Socket, var name: String?, val writer: PrintWriter, val reader: BufferedReader, var activated: Boolean = false)

class Server(port: Int) {

    val loginCode = "mc2912"
    val server: ServerSocket
    lateinit var reader: BufferedReader
    lateinit var writer: PrintWriter
    val clients: MutableList<Client> = mutableListOf()

    init {
        server = ServerSocket(port)
        println("${ChatColor.translateAlternateColorCodes('&', "&aServer started on port $port")}")
        Thread {
            while (true) {
                val socket = server.accept()
                println("${ChatColor.translateAlternateColorCodes('&', "&aClient connected")}")
                reader = BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8), 16384)
                writer = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), 16384), true)
                val client = Client(socket, null, writer, reader)
                clients.add(client)

                sendLoginToClient(client)
                readInput(client)
            }
        }.start()
    }

    fun sendLoginToClient(client: Client) {
        val json = JsonObject()
        json.addProperty("login", loginCode)
        client.writer.println(Packet(PacketType.LOGIN, json).createJsonPacket())
    }


    fun readInput(client: Client) {
        Thread {
            while (true) {
                val input = client.reader.readLine() ?: break
                val packet = Packet.fromJson(input)
                if (Minecraft.debug) println("${ChatColor.translateAlternateColorCodes('&', "&aClient sent: $input")}")
                if (!client.activated) {
                    if (packet.packetType == PacketType.LOGIN && packet.data.get("login").equals(loginCode)) client.activated = true
                    else {
                        var json = JsonObject()
                        json.addProperty("error", "Invalid login")
                        client.writer.println(Packet(PacketType.LOGIN, json).createJsonPacket())
                        continue
                    }
                }
                //Hier den Code ausführen!
                if (client.activated && client.name == null) {
                    client.name = input
                    println("${ChatColor.translateAlternateColorCodes('&', "&aClient registered as ${client.name}")}")
                    continue
                }
            }
        }.start()
    }


    fun start() {
        println("Server started")
    }
}
