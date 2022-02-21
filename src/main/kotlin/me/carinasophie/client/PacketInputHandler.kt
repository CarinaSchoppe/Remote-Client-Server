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

import javafx.application.Platform
import me.carinasophie.util.Dialog
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import me.carinasophie.util.grafics.Login

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
                    Login.stage.close()
                }
                return
            }
        }

    }


}
