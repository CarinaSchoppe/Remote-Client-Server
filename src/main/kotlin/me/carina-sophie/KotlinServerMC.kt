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
