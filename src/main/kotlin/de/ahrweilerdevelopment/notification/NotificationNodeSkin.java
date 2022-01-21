package de.ahrweilerdevelopment.notification;

import javafx.beans.binding.Bindings;
import javafx.css.PseudoClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NotificationNodeSkin extends SkinBase<NotificationNode> {

    private static final PseudoClass INFO_PSEUDO_CLASS = PseudoClass.getPseudoClass("info");
    private static final PseudoClass WARN_PSEUDO_CLASS = PseudoClass.getPseudoClass("warn");
    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");


    protected NotificationNodeSkin(NotificationNode notificationNode) {
        super(notificationNode);


        Label title = new Label(notificationNode.notification.title);
        StackPane close = new StackPane();
        close.getStyleClass().add("close");
        close.setOnMouseClicked(event -> notificationNode.notification.onClose.run());

        Label countLabel = new Label();
        StackPane icon = new StackPane();
        icon.getStyleClass().add("icon");

        switch (notificationNode.notification.type) {

            case INFO:
                pseudoClassStateChanged(INFO_PSEUDO_CLASS, true);
                break;
            case WARN:
                pseudoClassStateChanged(WARN_PSEUDO_CLASS, true);
                break;
            case ERROR:
                pseudoClassStateChanged(ERROR_PSEUDO_CLASS, true);
                break;
        }

        StackPane iconWrapper = new StackPane(icon, countLabel);
        countLabel.textProperty().bind(Bindings.size(notificationNode.notification.notifications).add(1).asString());
        countLabel.visibleProperty().bind(Bindings.isNotEmpty(notificationNode.notification.notifications));

        HBox header = new HBox(iconWrapper, title, close);
        header.getStyleClass().add("header");

        HBox actions = new HBox();
        notificationNode.notification.options.forEach((it) -> {
            Button button = new Button(it.toString());
            actions.getChildren().add(button);

            button.setOnAction(event -> {
                notificationNode.notification.complete(it);
                notificationNode.notification.onClose.run();
            });
        });

        VBox content = new VBox(header, new Label(notificationNode.notification.message), actions);

        getChildren().add(content);
        notificationNode.getStyleClass().add("notification");
    }
}