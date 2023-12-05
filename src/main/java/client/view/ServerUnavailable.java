package client.view;

import client.controller.Controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.*;
import java.awt.*;

import com.sun.net.httpserver.HttpExchange;

public class ServerUnavailable extends Stage{
    private Controller controller;
    VBox layout;
    private TextField error;
    private TextField server;
    private boolean serverOnline = false;

    public ServerUnavailable() {

        setTitle("Server Unavailable");
        setWidth(525);
        setHeight(650);

        this.error = new TextField("ERROR");
        error.setStyle("-fx-background-color: #93c994;-fx-alignment: center; -fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 80; -fx-font-family: 'Lucida Bright';");
        error.setVisible(true);
        error.setEditable(false);


        this.server = new TextField("Server is Unavailable");
        this.server.setStyle("-fx-background-color: #93c994;-fx-alignment: center; -fx-font-weight: bold; -fx-font-size: 40; -fx-font-family: 'Lucida Bright';");
        server.setVisible(true);
        server.setEditable(false);

        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(error, server);

        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);

        this.hide();
        
    }

    public boolean isOnline() {
        serverOnline = controller.checkServerStatus();
        return serverOnline;
    }

    public void displayIfOnline() {
        if (!serverOnline) {
            this.show();
        }
        else {
            this.hide();
        }
    }
}
