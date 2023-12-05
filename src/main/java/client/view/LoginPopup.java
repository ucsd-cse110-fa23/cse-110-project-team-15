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

public class LoginPopup extends Stage {
    private Button loginAccountButton;
    private TextField username;
    private TextField password;
    private String id;
    private HBox buttonBox;
    private VBox layout;
    private Label usernameLabel;
    private Label passwordLabel;
    private boolean loggedIn = false;
    private CheckBox autoLoginCheckbox;
    private boolean autoLogin = false;

    public LoginPopup() {

        setTitle("Login");
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


        loginAccountButton = new Button("Login");
        loginAccountButton.setStyle("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");

        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginAccountButton);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoginAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        loginAccountButton.setOnAction(eventHandler);
    }

    // public void loginAccount(String username, String password) {
    //     loggedIn = server.Login.loginAccount(username, password);
    //     if (loggedIn) {
    //         // If logged in successfully, close the login popup
    //         this.close();
    //         AppFrame.setLoggedInUI();
    //     }
    // }

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

    public String getId() {
        return this.id;
    }

    public String setId(String id) {
        return this.id = id;
    }

    public Button getLoginAccountButton() {
        return this.loginAccountButton;
    }

    public void setLoggedIn(boolean loggedin) {
        this.loggedIn = loggedin;
    }

    public String getAutologin() {
        return String.valueOf(autoLogin);
    }

    public void setUsername(String u) {
        this.username.setText(u);
    }

    public void setPassword(String p) {
        this.password.setText(p);
    }

    public void setAutologin(boolean a) {
        autoLogin = a;
    }

}