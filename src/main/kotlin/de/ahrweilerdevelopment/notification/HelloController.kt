package de.ahrweilerdevelopment.notification

import javafx.fxml.FXML
import javafx.scene.control.Label

class HelloController(private val nh: NotificationHandler) {


    @FXML
    private lateinit var welcomeText: Label

    @FXML
    fun initialize() {

        nh.info("Hallo", "Message", listOf("Test")).thenAccept(System.out::println)
        nh.info("Hallo", "Message", listOf("Test")).thenAccept(System.out::println)
        nh.info("Hallo", "Message2", listOf("Test")).thenAccept(System.out::println)
    }

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
        nh.error("Oops")
        nh.error("Oops")
        nh.error("Oops")
        nh.error("Oops")
    }
}