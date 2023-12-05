package client.view;

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
import javafx.geometry.Pos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.sun.net.httpserver.HttpExchange;

public class AccountPopup extends Stage {
    private Button createAccountButton;
    private TextField username;
    private TextField password;
    private HBox buttonBox;
    private VBox layout;
    private Label usernameLabel;
    private Label passwordLabel;
    private boolean loggedIn = false;
    private CheckBox autoLoginCheckbox;
    private boolean autoLogin = false;

    public AccountPopup() {

        setTitle("Create Account");
        setWidth(300);
        setHeight(300);

        usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-family: 'Lucida Bright';");
        usernameLabel.setVisible(true);

        passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-family: 'Lucida Bright';");
        passwordLabel.setVisible(true);

        username = new TextField("");
        password = new TextField("");

        username.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        password.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        autoLoginCheckbox = new CheckBox("Automatic Login");
        autoLoginCheckbox.setStyle("-fx-font-family: 'Lucida Bright';");
        autoLoginCheckbox.setOnAction(event -> {
            autoLogin = autoLoginCheckbox.isSelected();
        });

        createAccountButton = new Button("Create Account");
        createAccountButton.setStyle(
                "-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");

        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createAccountButton);


    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        createAccountButton.setOnAction(eventHandler);

    }

    public void display() {
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(usernameLabel, username, passwordLabel, password, autoLoginCheckbox, buttonBox);

        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);
        this.show();
    }

    public TextField getUsername() {
        return this.username;
    }

    public TextField getPassword() {
        return this.password;
    }

    public Button getCreateAccountButton() {
        return this.createAccountButton;
    }

    public String getAutologin() {
        return String.valueOf(autoLogin);
    }
}
