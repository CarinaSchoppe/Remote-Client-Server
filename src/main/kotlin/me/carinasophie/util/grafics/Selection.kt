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

package me.carinasophie.util.grafics

import com.google.gson.JsonObject
import javafx.beans.property.SimpleStringProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import javafx.stage.Stage
import me.carinasophie.client.Client
import me.carinasophie.server.minecraft.Player
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
    private lateinit var command: ChoiceBox<Any>

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
    lateinit var username: TableColumn<Player, String>

    @FXML
    private lateinit var refresh: Button

    @FXML
    lateinit var world: TableColumn<Player, String>

    @FXML
    fun onConsole(event: ActionEvent) {

    }

    @FXML
    fun onRefresh(event: ActionEvent) {
        val json = JsonObject()
        Client.instance.writer.println(Packet(PacketType.REFRESH, json).createJsonPacket())
    }

    @FXML
    fun onLogout(event: ActionEvent) {

    }

    @FXML
    fun onSend(event: ActionEvent) {

    }

    companion object {
        lateinit var instance: Selection
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

    @FXML
    fun initialize() {
        assert(command != null) { "fx:id=\"command\" was not injected: check your FXML file 'selection.fxml'." }
        assert(consoleButton != null) { "fx:id=\"consoleButton\" was not injected: check your FXML file 'selection.fxml'." }
        assert(food != null) { "fx:id=\"food\" was not injected: check your FXML file 'selection.fxml'." }
        assert(headText != null) { "fx:id=\"headText\" was not injected: check your FXML file 'selection.fxml'." }
        assert(health != null) { "fx:id=\"health\" was not injected: check your FXML file 'selection.fxml'." }
        assert(logoutButton != null) { "fx:id=\"logoutButton\" was not injected: check your FXML file 'selection.fxml'." }
        assert(pane != null) { "fx:id=\"pane\" was not injected: check your FXML file 'selection.fxml'." }
        assert(playerCoordinates != null) { "fx:id=\"playerCoordinates\" was not injected: check your FXML file 'selection.fxml'." }
        assert(reason != null) { "fx:id=\"reason\" was not injected: check your FXML file 'selection.fxml'." }
        assert(refresh != null) { "fx:id=\"refresh\" was not injected: check your FXML file 'selection.fxml'." }
        assert(sendButton != null) { "fx:id=\"sendButton\" was not injected: check your FXML file 'selection.fxml'." }
        assert(table != null) { "fx:id=\"table\" was not injected: check your FXML file 'selection.fxml'." }
        assert(username != null) { "fx:id=\"username\" was not injected: check your FXML file 'selection.fxml'." }
        assert(world != null) { "fx:id=\"world\" was not injected: check your FXML file 'selection.fxml'." }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        instance = this
        health.setCellValueFactory { SimpleStringProperty(it.value.health.toString()) }
        food.setCellValueFactory { SimpleStringProperty(it.value.food.toString()) }
        world.setCellValueFactory { SimpleStringProperty(it.value.world) }
        playerCoordinates.setCellValueFactory { SimpleStringProperty(it.value.coordinates.toString()) }
        username.setCellValueFactory { SimpleStringProperty(it.value.name) }

    }


    fun update() {
        table.items.clear()
        table.items.addAll(players)
    }
}