/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 14:12 by Carina The Latest changes made by Carina on 21.02.22, 14:12.
 *  All contents of "LeaveAndJoin.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.server.minecraft.events

import me.carinasophie.Minecraft
import me.carinasophie.server.PacketMessageManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class LeaveAndJoin : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        for (client in Minecraft.server.loggedInClients) PacketMessageManager.refreshPlayers(client, Bukkit.getOnlinePlayers().toTypedArray())
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        val players: MutableList<Player> = mutableListOf()
        players.addAll(Bukkit.getOnlinePlayers())
        players.remove(event.player)
        for (client in Minecraft.server.loggedInClients) PacketMessageManager.refreshPlayers(client, players.toTypedArray())
    }

}
