package client.view;

import javafx.stage.Stage;
import server.CreateAccount;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
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
    private static VBox layout;
    private Label usernameLabel;
    private Label passwordLabel;

    public AccountPopup() {

        setTitle("Create Account");
        setWidth(300);
        setHeight(200);

        usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        usernameLabel.setVisible(true);

        passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        passwordLabel.setVisible(true);

        username = new TextField("");
        password = new TextField("");

        username.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        password.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        createAccountButton = new Button("Create Account");

        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createAccountButton);

        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(usernameLabel, username, passwordLabel, password, buttonBox);

    }

    public void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler) {
        createAccountButton.setOnAction(event -> {
            String enteredUsername = username.getText();
            String enteredPassword = password.getText();
            createAccount(enteredUsername, enteredPassword);
    
        });

    }

    private void createAccount(String username, String password) {
        // Use the entered username and password and send it to the Create class
        server.CreateAccount.createAccount(username, password);
        sendDataToServerAndMongoDB(username, password);
    }


    private void sendDataToServerAndMongoDB(String username, String password) {
        try {
            // Sending data to your server using HttpClient
            String serverUrl = "http://localhost:8100/create_account"; // Replace with your server URL
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl))
                    .POST(HttpRequest.BodyPublishers.ofString("username=" + username + "&password=" + password))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            // Handle response from server if needed
            System.out.println("Server Response - Status code: " + statusCode);
            System.out.println("Server Response - Body: " + responseBody);
            System.out.println("Data inserted into MongoDB");

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }
    
    public void display() {
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
}