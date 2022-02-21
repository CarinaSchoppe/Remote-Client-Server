/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "Dialog.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType


object Dialog {
    fun show(message: String?, title: String?, dialogType: DialogType) {
        val alert = Alert(dialogType.toAlertType(), message)
        alert.title = title
        alert.headerText = title
        alert.showAndWait()
    }

    enum class DialogType(private val alertType: AlertType) {
        INFO(AlertType.INFORMATION), ERROR(AlertType.ERROR);

        fun toAlertType(): AlertType {
            return alertType
        }
    }
}
