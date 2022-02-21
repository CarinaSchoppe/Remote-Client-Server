/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 11:00 by Carina The Latest changes made by Carina on 21.02.22, 11:00.
 *  All contents of "Logger.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.server.minecraft

import me.carinasophie.Minecraft
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.appender.AbstractAppender

open class Logger : AbstractAppender {
    constructor() : super("KotlinServerClientGUILogger", null, null)

    init {
        start()
    }

    override fun append(event: LogEvent) {
        val log = event.toImmutable()
        println(Minecraft.prefix + log.message.formattedMessage)
    }
}
