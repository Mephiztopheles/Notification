package de.ahrweilerdevelopment.notification;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Notification {


    final String title;
    final String message;
    final Type type;
    final List<Object> options = new ArrayList<>();

    CompletableFuture<Object> action = new CompletableFuture<>();
    Runnable onClose = () -> {
    };

    final ObservableList<Notification> notifications = FXCollections.observableArrayList();

    public Notification(String title, String message, Type type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        this.type = Type.INFO;
    }

    public Notification(String title, String message, Type type, List<?> options) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.options.addAll(options);
    }


    void append(Notification notification) {
        notifications.add(notification);
    }


    void complete(Object it) {
        action.complete(it);
        notifications.forEach(notification -> notification.action.complete(it));
    }
}