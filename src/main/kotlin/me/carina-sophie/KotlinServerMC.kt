/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 12:27 by Carina The Latest changes made by Carina on 20.02.22, 12:24 All contents of "KotlinServerMC.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.`carina-sophie`

import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class KotlinServerMC : JavaPlugin() {
    override fun onDisable() {
        println("${ChatColor.translateAlternateColorCodes('&', "&c&lKotlinServerMC &7>> &c&lDisabled")}")
    }

    override fun onEnable() {
    }


}

fun main() {
    println("Test")
}
