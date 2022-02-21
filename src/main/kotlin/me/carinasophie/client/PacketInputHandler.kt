/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 00:00 by Carina The Latest changes made by Carina on 21.02.22, 00:00.
 *  All contents of "PacketInputHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.client

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.application.Platform
import javafx.stage.Stage
import me.carinasophie.server.minecraft.Player
import me.carinasophie.util.Dialog
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import me.carinasophie.util.grafics.Console
import me.carinasophie.util.grafics.Login
import me.carinasophie.util.grafics.Selection


object PacketInputHandler {

    fun handlePacket(packet: Packet) {
        println("Server has send: ${packet.createJsonPacket()}")
        when (packet.packetType) {
            PacketType.LOGIN -> {
                if (!packet.data.get("magic").asString.equals("mc2912")) {
                    Platform.runLater { Dialog.show("Wrong Server", "Error", Dialog.DialogType.ERROR) }
                    Client.instance.disconnect()
                }
                return
            }
            PacketType.ERROR -> {
                if (packet.data.get("action").asString.equals("disconnect")) {
                    Platform.runLater { Dialog.show(packet.data.get("message").asString, "Error", Dialog.DialogType.ERROR) }
                    Client.instance.disconnect()
                }
                return
            }
            PacketType.SUCCESS -> {
                Platform.runLater {
                    Console().start(Login.stage.scene.window as Stage)
                }
                return
            }
            PacketType.LOG -> {
                Platform.runLater {
                    if (Console.instance != null) {
                        Console.instance!!.consoleWindow.text += packet.data.get("log").asString + "\n"
                    }
                }
                return
            }
            PacketType.REFRESH -> {
                Platform.runLater {
                    if (Selection.instance != null) {
                        refresh(packet)
                    }
                }
                return
            }
        }

    }

    fun refresh(packet: Packet) {
        //Convert a jsonString to a JsonObject
        val myType = object : TypeToken<List<Player>>() {}.type
        val test = Gson().fromJson<List<Player>>(packet.data.get("players"), myType)
        Selection.players = test
        Selection.instance.update()
    }


}
