/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 14:29.
 *  All contents of "KotlinServerMC.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie

import me.carinasophie.util.graphics.Login

class KotlinServerMC {
    init {
        println("KotlinServerMC-System started!")
        Login().create()
    }
}

fun main() {
//Create a new instance of the plugin
    KotlinServerMC()
}
