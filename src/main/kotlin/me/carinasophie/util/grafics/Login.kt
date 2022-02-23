/*
 * Copyright Notice for KotlinServerClientMinecraft
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 20.02.22, 15:38 by Carina The Latest changes made by Carina on 20.02.22, 15:38.
 *  All contents of "Login.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package me.carinasophie.util.grafics

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import me.carinasophie.client.Client
import me.carinasophie.util.Dialog
import java.net.URL
import java.util.*

class Login : Application(), Initializable {

    companion object {
        lateinit var stage: Stage
        lateinit var instance: Login

    }

    @FXML
    private lateinit var resources: ResourceBundle

    @FXML
    private lateinit var location: URL

    @FXML
    private lateinit var ip: TextField

    @FXML
    private lateinit var loginButton: Button

    @FXML
    private lateinit var pane: AnchorPane

    @FXML
    private lateinit var password: PasswordField

    @FXML
    private lateinit var port: TextField

    @FXML
    private lateinit var username: TextField

    @FXML
    fun onLogin(event: ActionEvent) {
        if (ip.text.isEmpty() || port.text.isEmpty() || username.text.isEmpty() || password.text.isEmpty()) {
            println("Please fill all fields")
            Dialog.show("Please fill all fields", "Login-Error", Alert.AlertType.ERROR)
            return
        }
        try {
            port.text.toInt()
        } catch (e: Exception) {
            Dialog.show("Please enter a valid port", "Port-Error", Alert.AlertType.ERROR)
        }

        val client = Client(ip = ip.text, port = port.text.toInt(), name = username.text, password = password.text)
        client.start()

    }

    @FXML
    fun initialize() {
        assert(ip != null) { "fx:id=\"ip\" was not injected: check your FXML file 'login.fxml'." }
        assert(loginButton != null) { "fx:id=\"loginButton\" was not injected: check your FXML file 'login.fxml'." }
        assert(pane != null) { "fx:id=\"pane\" was not injected: check your FXML file 'login.fxml'." }
        assert(password != null) { "fx:id=\"password\" was not injected: check your FXML file 'login.fxml'." }
        assert(port != null) { "fx:id=\"port\" was not injected: check your FXML file 'login.fxml'." }
        assert(username != null) { "fx:id=\"username\" was not injected: check your FXML file 'login.fxml'." }
    }


    fun create() = launch()

    /*Dialog.show("Du musst erst das Spiel voll konfigurieren!", "Fehler!", Dialog.DialogType.ERROR);
     */
    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/grafics/login.fxml"))
        loader.setController(this)
        val root: Parent = loader.load()
        initialize()
        primaryStage.isResizable = false
        primaryStage.title = "Login"
        primaryStage.scene = Scene(root)
        stage = primaryStage
        primaryStage.show()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        instance = this
    }


}
