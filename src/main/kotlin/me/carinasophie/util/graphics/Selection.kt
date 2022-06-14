/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 11:50 by Carina The Latest changes made by Carina on 21.02.22, 11:50.
 *  All contents of "Selection.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util.graphics

import com.google.gson.JsonObject
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import javafx.stage.Stage
import me.carinasophie.client.Client
import me.carinasophie.server.minecraft.Player
import me.carinasophie.util.Dialog
import me.carinasophie.util.Packet
import me.carinasophie.util.PacketType
import java.net.URL
import java.util.*

class Selection : Initializable {

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var command: ChoiceBox<String>

    @FXML
    private lateinit var consoleButton: Button

    @FXML
    lateinit var food: TableColumn<Player, String>

    @FXML
    private lateinit var headText: Text

    @FXML
    lateinit var health: TableColumn<Player, String>

    @FXML
    private lateinit var logoutButton: Button

    @FXML
    private lateinit var pane: AnchorPane

    @FXML
    lateinit var playerCoordinates: TableColumn<Player, String>

    @FXML
    private lateinit var reason: TextField

    @FXML
    private lateinit var sendButton: Button

    @FXML
    lateinit var table: TableView<Player>

    @FXML
    private lateinit var level: TableColumn<Player, String>

    @FXML
    lateinit var username: TableColumn<Player, String>

    @FXML
    private lateinit var ping: TableColumn<Player, String>

    @FXML
    private lateinit var refresh: Button

    @FXML
    private lateinit var gamemode: TableColumn<Player, String>

    @FXML
    lateinit var world: TableColumn<Player, String>

    @FXML
    private lateinit var chatButton: Button

    @FXML
    fun onChat(event: ActionEvent) {
        val primaryStage = (event.source as Node).scene.window as Stage
        Chat().start(primaryStage)
    }

    @FXML
    fun onConsole(event: ActionEvent) {
        val primaryStage = (event.source as Node).scene.window as Stage
        Console().start(primaryStage)
    }

    @FXML
    fun onRefresh(event: ActionEvent) {
        val json = JsonObject()
        Client.instance.writer.println(Packet(PacketType.REFRESH, json).createJsonPacket())
    }

    @FXML
    fun onLogout(event: ActionEvent) {
        Client.instance.logout()
        val primaryStage = (event.source as Node).scene.window as Stage
        Login.instance.start(primaryStage)
    }

    @FXML
    fun onSend(event: ActionEvent) {
        if (table.selectionModel.selectedItem == null) {
            Dialog.show("Select a player!", "Selection-Error", Alert.AlertType.ERROR)
            return
        }

        when (command.value) {
            "Kick" -> {
                if (reasonCheck()) return
                val json = JsonObject()
                json.addProperty("command", "/kick ${table.selectionModel.selectedItem!!.name} ${reason.text}")
                Client.instance.writer.println(Packet(PacketType.COMMAND, json).createJsonPacket())

            }
            "Mute" -> {
                if (reasonCheck()) return
                val json = JsonObject()
                json.addProperty("command", "/mute ${table.selectionModel.selectedItem!!.name} ${reason.text}")
                Client.instance.writer.println(Packet(PacketType.COMMAND, json).createJsonPacket())

            }
            "Unmute" -> {
                val json = JsonObject()
                json.addProperty("command", "/unmute ${table.selectionModel.selectedItem!!.name}")
                Client.instance.writer.println(Packet(PacketType.COMMAND, json).createJsonPacket())

            }
            "Ban" -> {
                if (reasonCheck()) return
                val json = JsonObject()
                json.addProperty("command", "/ban ${table.selectionModel.selectedItem!!.name} ${reason.text}")
                Client.instance.writer.println(Packet(PacketType.COMMAND, json).createJsonPacket())

            }
            "Unban" -> {
                val json = JsonObject()
                json.addProperty("command", "/unban ${table.selectionModel.selectedItem!!.name}")
                Client.instance.writer.println(Packet(PacketType.COMMAND, json).createJsonPacket())

            }

            "Warn" -> {
                if (reasonCheck()) return
                val json = JsonObject()
                json.addProperty("command", "/warn ${table.selectionModel.selectedItem!!.name} ${reason.text}")
                Client.instance.writer.println(Packet(PacketType.COMMAND, json).createJsonPacket())

            }
        }

    }

    private fun reasonCheck(): Boolean {
        if (reason.text == null) {
            Dialog.show("Enter a reason!", "Reason-Error", Alert.AlertType.ERROR)
            return true
        }
        return false
    }

    companion object {
        var instance: Selection? = null
        var players = listOf<Player>()
    }

    fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/grafics/selection.fxml"))
        loader.setController(this)
        val root = loader.load<Any>() as Parent
        primaryStage.title = "Selection"
        primaryStage.isResizable = false
        primaryStage.scene = Scene(root)
        primaryStage.show()
        val json = JsonObject()
        Client.instance.writer.println(Packet(PacketType.REFRESH, json).createJsonPacket())
    }


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        instance = this
        health.setCellValueFactory { SimpleStringProperty(it.value.health.toString()) }
        food.setCellValueFactory { SimpleStringProperty(it.value.food.toString()) }
        world.setCellValueFactory { SimpleStringProperty(it.value.world) }
        playerCoordinates.setCellValueFactory { SimpleStringProperty(it.value.coordinates.toString()) }
        username.setCellValueFactory { SimpleStringProperty(it.value.name) }
        level.setCellValueFactory { SimpleStringProperty(it.value.level.toString()) }
        ping.setCellValueFactory { SimpleStringProperty(it.value.ping.toString()) }
        gamemode.setCellValueFactory { SimpleStringProperty(it.value.gamemode) }

        command.items.add("Mute")
        command.items.add("Unmute")
        command.items.add("Kick")
        command.items.add("Ban")
        command.items.add("Unban")
        command.items.add("Warn")


    }


    fun update() {
        table.items.clear()
        table.items.addAll(players)
    }
}
