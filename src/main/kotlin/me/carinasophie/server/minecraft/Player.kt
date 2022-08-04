/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 12:00 by Carina The Latest changes made by Carina on 21.02.22, 10:27.
 *  All contents of "Player.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.server.minecraft

data class Coordinates(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String {
        return "(X:$x, Y:$y, Z:$z)"
    }
}

data class Player(val name: String, val health: Int, val food: Int, val coordinates: Coordinates, val world: String, val gamemode: String, val level: Int, val ping: Int) {
    override fun toString(): String {
        return "Player(name='$name', health=$health, food=$food, coordinates=$coordinates, world='$world', gamemode='$gamemode', level=$level, ping=$ping)"
    }
}
