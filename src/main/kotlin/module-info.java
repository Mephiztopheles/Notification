module de.ahrweilerdevelopment.notification {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens de.ahrweilerdevelopment.notification to javafx.fxml;
    exports de.ahrweilerdevelopment.notification;
}