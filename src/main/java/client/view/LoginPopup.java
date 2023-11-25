package client.view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

public class LoginPopup extends Stage {
    private Button loginButton;
    private TextField username;
    private TextField password;
    private HBox buttonBox;
    private static VBox layout;
    private Label usernameLabel;
    private Label passwordLabel;

    public LoginPopup() {

        setTitle("Login");
        setWidth(300);
        setHeight(200);

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

        loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");

        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton);

        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(usernameLabel, username, passwordLabel, password, buttonBox);
    }

    public void display() {
        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);
        this.show();
    }

    public Label getusernameLabel() {
        return this.usernameLabel;
    }

    public Label getpasswordLabel() {
        return this.passwordLabel;
    }

    public Button getloginButton() {
        return this.loginButton;
    }
}