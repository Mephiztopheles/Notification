package de.ahrweilerdevelopment.notification

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Callback

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val nh = NotificationHandler(stage)
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        fxmlLoader.controllerFactory = Callback { HelloController(nh) }
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}