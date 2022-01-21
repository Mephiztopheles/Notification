package de.ahrweilerdevelopment.notification;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NotificationHandler {

    private final Popup popup = new Popup();
    private final VBox notificationRoot = new VBox();

    private final Window owner;

    private final ObservableList<Notification> notifications = FXCollections.observableArrayList();

    public NotificationHandler(Window owner) {

        this.owner = owner;

        popup.getContent().add(notificationRoot);
        URL stylesheet = getClass().getResource("Notification.css");
        if (stylesheet != null)
            popup.getScene().setUserAgentStylesheet(stylesheet.toExternalForm());

        owner.xProperty().addListener((o, oldValue, newValue) -> reposition());
        owner.yProperty().addListener((o, oldValue, newValue) -> reposition());
        owner.widthProperty().addListener((o, oldValue, newValue) -> reposition());
        owner.heightProperty().addListener((o, oldValue, newValue) -> reposition());

        owner.focusedProperty().addListener((o, oldValue, newValue) -> {
            if (newValue)
                popup.show(owner);
            else
                popup.hide();
            reposition();
        });

        notificationRoot.getStyleClass().add("root");

        notifications.addListener((ListChangeListener<? super Notification>) (change) -> {
            while (change.next()) {
                if (change.wasAdded())
                    change.getAddedSubList().forEach(it -> notificationRoot.getChildren().add(change.getFrom(), new NotificationNode(it)));
                if (change.wasRemoved())
                    notificationRoot.getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
            }

            popup.show(owner);
            reposition();
        });
    }


    void error(String title) {
        add(new Notification(title, "", Type.ERROR));
    }

    void info(String title) {
        add(new Notification(title, ""));
    }

    CompletableFuture<?> info(String title, String message, List<?> options) {

        Notification notification = add(new Notification(title, message, Type.INFO, options));

        return notification.action;
    }

    private Notification add(Notification notification) {

        int indexOf = notifications.indexOf(notification);
        if (indexOf == -1) {

            notifications.add(notification);

            notification.onClose = () -> notifications.remove(notification);

        } else
            notifications.get(indexOf).append(notification);

        return notification;
    }

    private void reposition() {

        if (!popup.isShowing())
            return;

        Point2D localToScreen = owner.getScene().getRoot().localToScreen(0.0, 0.0);
        popup.setAnchorX(localToScreen.getX());
        popup.setAnchorY(localToScreen.getY());
    }
}