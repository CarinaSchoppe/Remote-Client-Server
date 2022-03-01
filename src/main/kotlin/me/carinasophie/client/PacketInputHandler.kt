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
import javafx.scene.control.Alert
import javafx.stage.Stage
import me.carinasophie.server.minecraft.Player
import me.carinasophie.util.Dialog
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import me.carinasophie.util.grafics.Chat
import me.carinasophie.util.grafics.Console
import me.carinasophie.util.grafics.Login
import me.carinasophie.util.grafics.Selection


object PacketInputHandler {

    fun handlePacket(packet: Packet) {
        when (packet.packetType) {
            PacketType.LOGIN -> {
                if (!packet.data.get("magic").asString.equals(Client.instance.magicCode)) {
                    Platform.runLater { Dialog.show("Wrong Server", "Error", Alert.AlertType.ERROR) }
                    Client.instance.disconnect()
                }
            }
            PacketType.ERROR -> {
                if (packet.data.get("action").asString.equals("disconnect")) {
                    Platform.runLater { Dialog.show(packet.data.get("message").asString, "Error", Alert.AlertType.ERROR) }
                    Client.instance.disconnect()
                }
            }
            PacketType.SUCCESS -> {
                Platform.runLater {
                    Console().start(Login.stage.scene.window as Stage)
                }
            }
            PacketType.LOG -> {
                Platform.runLater {
                    Console.text += packet.data.get("log").asString.replace("§", "&") + "\n"
                    if (Console.instance != null) {
                        Console.instance!!.consoleWindow.text = Console.text
                    }
                }
            }
            PacketType.REFRESH -> {
                Platform.runLater {
                    if (Selection.instance != null) {
                        refresh(packet)
                    }
                }
            }
            PacketType.INFO -> {
                Platform.runLater {
                    if (packet.data.get("info").asJsonObject.get("type").asString == "success")
                        Dialog.show(packet.data.get("info").asJsonObject.get("text").asString, packet.data.get("info").asJsonObject.get("title").asString, Alert.AlertType.INFORMATION)
                    else if ((packet.data.get("info").asJsonObject.get("type").asString == "fail"))
                        Dialog.show(packet.data.get("info").asJsonObject.get("text").asString, packet.data.get("info").asJsonObject.get("title").asString, Alert.AlertType.ERROR)
                    else if ((packet.data.get("info").asJsonObject.get("type").asString == "warn"))
                        Dialog.show(packet.data.get("info").asJsonObject.get("text").asString, packet.data.get("info").asJsonObject.get("title").asString, Alert.AlertType.WARNING)

                }
            }

            PacketType.CHAT -> {
                Platform.runLater {
                    Chat.text += packet.data.get("player").asString + " >> " + packet.data.get("message").asString + "\n"
                    if (Chat.instance != null) {
                        Chat.instance.consoleWindow.text = Chat.text
                    }
                }
            }
            PacketType.LOGOUT -> {
                Client.instance.disconnect()
            }
        }

    }

    private fun refresh(packet: Packet) {
        val myType = object : TypeToken<List<Player>>() {}.type
        val test = Gson().fromJson<List<Player>>(packet.data.get("players"), myType)
        Selection.players = test
        Selection.instance!!.update()
    }


}
