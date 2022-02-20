/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 18:40 by Carina The Latest changes made by Carina on 20.02.22, 18:40.
 *  All contents of "User.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

class User(val username: String, val password: String) {
    override fun toString(): String {
        return "User(username='$username':password='$password')"
    }
}
