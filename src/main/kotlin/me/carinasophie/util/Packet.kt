/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:34.
 *  All contents of "Packet.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.carinasophie.server.Client

enum class PacketType(val code: String) {
    INFO("info"),
    LOGIN("login"),
    COMMAND("command"),
    CHAT("chat"),
    ERROR("error"),
    SUCCESS("success")

}




class Packet(val packetType: PacketType, val data: JsonObject) {
    companion object {
        fun send(client: Client, type: PacketType, contents: JsonObject) {
            client.writer.println(Packet(type, contents).createJsonPacket())
        }

        fun fromJson(input: String): Packet {
            val json = Gson().fromJson(input, JsonObject::class.java)
            val type = PacketType.values().first { it.code == json.get("type").asString }
            return Packet(type, json.get("data").asJsonObject)
        }
    }

    fun createJsonPacket(): String {
        val json = JsonObject()
        json.addProperty("type", packetType.code)
        json.add("data", data)
        return json.toString()
    }
}
