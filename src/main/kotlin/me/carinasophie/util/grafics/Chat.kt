/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 15:33 by Carina The Latest changes made by Carina on 21.02.22, 15:33.
 *  All contents of "Chat.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util.grafics

import com.google.gson.JsonObject
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import javafx.stage.Stage
import me.carinasophie.client.Client
import me.carinasophie.util.Dialog
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import java.net.URL
import java.util.*

class Chat : Initializable {

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var chat: TextField

    @FXML
    private lateinit var consoleButton: Button

    @FXML
    lateinit var consoleWindow: TextArea

    @FXML
    private lateinit var headText: Text

    @FXML
    private lateinit var logoutButton: Button

    @FXML
    private lateinit var pane: AnchorPane

    @FXML
    private lateinit var playerMenuButton: Button

    @FXML
    private lateinit var sendButton: Button

    @FXML
    fun onConsole(event: ActionEvent) {
        val primaryStage = (event.source as Node).scene.window as Stage
        Console().start(primaryStage)
    }

    @FXML
    fun onLogout(event: ActionEvent) {
        Client.instance.logout()
        val primaryStage = (event.source as Node).scene.window as Stage
        Login.instance.start(primaryStage)

    }

    @FXML
    fun onMenu(event: ActionEvent) {
        val primaryStage = (event.source as Node).scene.window as Stage
        Selection().start(primaryStage)
    }

    @FXML
    fun onSend(event: ActionEvent) {
        if (chat.text == null) {
            Dialog.show("Enter a Chatmessage!", "Chat-Error", Alert.AlertType.ERROR)
            return
        }
        val json = JsonObject()
        json.addProperty("chat", "${chat.text}")
        Client.instance.writer.println(Packet(PacketType.CHAT, json).createJsonPacket())
    }

    companion object {
        lateinit var instance: Chat
        var text = ""
    }

    fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/grafics/chat.fxml"))
        loader.setController(this)
        val root = loader.load<Any>() as Parent
        primaryStage.title = "Console"
        primaryStage.isResizable = false
        primaryStage.scene = Scene(root)
        consoleWindow.text = text
        primaryStage.show()
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        instance = this
    }

}
