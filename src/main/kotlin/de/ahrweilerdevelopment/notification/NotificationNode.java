package de.ahrweilerdevelopment.notification;

import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class NotificationNode extends Control {

    final Notification notification;

    public NotificationNode(Notification notification) {
        this.notification = notification;
    }

    public Skin<?> createDefaultSkin() {
        return new NotificationNodeSkin(this);
    }
}