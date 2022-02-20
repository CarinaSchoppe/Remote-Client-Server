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
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

class Login : Application() {

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
        var root: Parent = loader.load()
        initialize()
        primaryStage.isResizable = false
        primaryStage.title = "Login"
        primaryStage.scene = Scene(root)
        primaryStage.show()
        print("test")
    }

}
