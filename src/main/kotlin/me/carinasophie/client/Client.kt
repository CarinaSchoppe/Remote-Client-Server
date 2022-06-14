/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "Client.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.client

import com.google.gson.JsonObject
import javafx.scene.control.Alert
import me.carinasophie.util.Dialog
import me.carinasophie.util.Encoder
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import java.nio.charset.StandardCharsets

class Client(private val name: String, private val ip: String, private val port: Int, private val password: String) {

    private var socket: Socket? = null
    private lateinit var reader: BufferedReader
    lateinit var writer: PrintWriter
    val magicCode = "mc2912"

    companion object {
        lateinit var instance: Client
    }

    init {
        try {
            socket = Socket(ip, port)
            println("Client $name connected to $ip:$port")

        } catch (e: Exception) {
            Dialog.show(dialogType = Alert.AlertType.ERROR, title = "Connection failed", message = "Could not connect to server")
        }
    }

    fun logout() {
        val json = JsonObject()
        writer.println(Packet(PacketType.LOGOUT, json).createJsonPacket())
    }

    fun disconnect() {
        if (socket != null) {
            socket!!.close()
            socket = null
            println("Client \"$name\" disconnected from $ip:$port")
        }
    }

    fun start() {
        if (socket == null)
            return
        if (socket!!.isConnected) {
            instance = this
            reader = BufferedReader(InputStreamReader(socket!!.getInputStream(), StandardCharsets.UTF_8), 16384)
            writer = PrintWriter(OutputStreamWriter(socket!!.getOutputStream(), StandardCharsets.UTF_8), true)
            login()
            read()
        }
    }

    private fun login() {
        val json = JsonObject()
        val info = JsonObject()
        info.addProperty("username", name)
        info.addProperty("password", password)
        json.addProperty("magic", magicCode)
        json.add("login", info)
        writer.println(Packet(PacketType.LOGIN, json).createJsonPacket())

    }

    private fun read() {
        Thread {
            while (true) {
                var input: String?
                try {
                    input = Encoder.decode(reader.readLine())
                } catch (e: Exception) {
                    disconnect()
                    return@Thread
                }
                PacketInputHandler.handlePacket(Packet.fromJson(input))
            }
        }.start()
    }

}
