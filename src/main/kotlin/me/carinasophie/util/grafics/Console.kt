/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.02.22, 11:10 by Carina The Latest changes made by Carina on 21.02.22, 11:10.
 *  All contents of "Console.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util.grafics

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import javafx.stage.Stage
import java.net.URL
import java.util.*


class Console : Initializable {

    companion object {
        var instance: Console? = null
    }

    @FXML
    lateinit var consoleWindow: TextArea

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var command: TextField

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
    fun onLogout(event: ActionEvent) {

    }

    @FXML
    fun onMenu(event: ActionEvent) {

    }

    @FXML
    fun onSend(event: ActionEvent) {

    }

    @FXML
    fun initialize() {
        assert(command != null) { "fx:id=\"command\" was not injected: check your FXML file 'console.fxml'." }
        assert(consoleWindow != null) { "fx:id=\"consoleWindow\" was not injected: check your FXML file 'console.fxml'." }
        assert(headText != null) { "fx:id=\"headText\" was not injected: check your FXML file 'console.fxml'." }
        assert(logoutButton != null) { "fx:id=\"logoutButton\" was not injected: check your FXML file 'console.fxml'." }
        assert(pane != null) { "fx:id=\"pane\" was not injected: check your FXML file 'console.fxml'." }
        assert(playerMenuButton != null) { "fx:id=\"playerMenuButton\" was not injected: check your FXML file 'console.fxml'." }
        assert(sendButton != null) { "fx:id=\"sendButton\" was not injected: check your FXML file 'console.fxml'." }
        print("Console initialized")
    }

    fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/grafics/console.fxml"))
        loader.setController(this)
        val root = loader.load<Any>() as Parent
        primaryStage.title = "Console"
        primaryStage.isResizable = false
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        instance = this
    }

}
