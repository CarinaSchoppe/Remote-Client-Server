/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 25.02.22, 09:44 by Carina The Latest changes made by Carina on 25.02.22, 09:44.
 *  All contents of "Encoder.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import java.util.*

object Encoder {

    fun encode(input: String): String {
        val encode = Base64.getEncoder().encodeToString(input.toByteArray())
        return encode
    }

    fun decode(input: String): String {
        return String(Base64.getDecoder().decode(input))
    }


}
