package client.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.ChatGPT;
import server.Server;
import server.Whisper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.*;
import javafx.scene.control.Label;
import java.io.*;

import javax.swing.text.View;

class Footer extends HBox {

    private Button createButton;

    Footer() {
        this.setPrefSize(600, 60);
        this.setStyle("-fx-background-color: #93c994;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = ("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");

        createButton = new Button("+ New Recipe"); // text displayed on add button
        createButton.setStyle(defaultButtonStyle); // styling the button

        this.getChildren().addAll(createButton);
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
    }

    public Button getCreateButton() {
        return createButton;
    }
}

class Header extends HBox {
    private Button createAccountButton;
    private Button loginButton;
    private Button logoutButton;
    boolean loggedIn = false;
    private LoginPopup loginPopup;
    Label username;
    private VBox headerContent;

    Header() {
        this.setPrefSize(600, 100); // Size of the header
        this.setStyle("-fx-background-color: #93c994;");
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = ("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");
        createAccountButton = new Button("Create Account"); // text displayed on account button
        createAccountButton.setStyle(defaultButtonStyle);
        // createAccountButton.setAlignment(Pos.CENTER_RIGHT);

        loginButton = new Button("Login");
        loginButton.setStyle(defaultButtonStyle);

        logoutButton = new Button("Logout");
        logoutButton.setStyle(defaultButtonStyle);
        logoutButton.setVisible(false);

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold;  -fx-font-size: 25; -fx-font-family: 'Lucida Bright';");

        loginPopup = new LoginPopup();
        username = new Label();
        username.setStyle("-fx-font-size: 13; -fx-font-family: 'Lucida Bright';");
        username.setAlignment(Pos.CENTER_RIGHT);
        username.setVisible(false);

        HBox buttons = new HBox(createAccountButton, loginButton, logoutButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));

        headerContent = new VBox(titleText, username, buttons);
        headerContent.setAlignment(Pos.CENTER);

        this.getChildren().addAll(headerContent);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }

    public Button getCreateAccountButton(boolean visible) {
        createAccountButton.setVisible(visible);
        return createAccountButton;

    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Label getUsername() {
        return username;
    }

    public Button getLogoutButton(boolean visible) {
        logoutButton.setVisible(visible);
        return logoutButton;
    }

    public Button getLoginButton(boolean visible) {
        loginButton.setVisible(visible);
        return loginButton;
    }
}

public class AppFrame extends BorderPane {

    public static Header header;
    private Footer footer;
    private static RecipeList recipeList;
    private RecipePopup recipePopup;
    private AccountPopup accountPopup;
    private DetailsPopup detailsPopup;
    private LoginPopup loginPopup;
    private ServerUnavailable serverUnavailable;

    private Button createButton;
    private Button createAccountButton;
    private Button loginButton;
    private Button logoutButton;

    public AppFrame() {
        // Initialise the header Object
        header = new Header();

        // Create a RecipeList Object to hold the tasks
        recipeList = new RecipeList(this);

        // Initialise the Footer Object
        footer = new Footer();

        recipePopup = new RecipePopup();

        accountPopup = new AccountPopup();
        detailsPopup = new DetailsPopup();
        accountPopup = new AccountPopup();
        loginPopup = new LoginPopup();
        serverUnavailable = new ServerUnavailable();

        ScrollPane scroll = new ScrollPane(recipeList);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroll);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        createButton = footer.getCreateButton();
        createAccountButton = header.getCreateAccountButton(true);
        loginButton = header.getLoginButton();
        logoutButton = header.getLogoutButton(false);

        // recipeList.loadTasks();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners() {
        createButton.setOnAction(e -> {
            Recipe recipe = new Recipe(this);
            // recipeList.getChildren().add(recipe);
            recipePopup.setRecipe(recipe);
            recipePopup.display();
        });
        createAccountButton.setOnAction(e -> {
            accountPopup.display();
        });
        loginButton.setOnAction(e -> {
            loginPopup.display();
        });
        logoutButton.setOnAction(e -> {
            server.Account.clearCredentials();
            setLoggedOutUI();
        });
    }

    public void setLoggedInUI() {
        header.getLogoutButton(true);
        header.getLoginButton(false);
        header.getCreateAccountButton(false);
        header.getUsername().setText("@" + loginPopup.getUsername().getText());
        header.getUsername().setVisible(true);
        recipeList.clearRecipes();
        getRecipes().loadTasks(loginPopup.getId());

    }

    public void setLoggedOutUI() {
        header.getLogoutButton(false);
        header.getLoginButton(true);
        header.getCreateAccountButton(true);
        header.getUsername().setVisible(false);
        recipeList.clearRecipes();
    }

    public static RecipeList getRecipes() {
        return recipeList;
    }

    public RecipePopup getRecipePopup() {
        return recipePopup;
    }

    public DetailsPopup getDetailsPopup() {
        return detailsPopup;
    }

    public AccountPopup getAccountPopup() {
        return accountPopup;
    }

    public LoginPopup getLoginPopup() {
        return loginPopup;
    }

    public ServerUnavailable getServerUnavailable() {
        if (serverUnavailable == null) {
            serverUnavailable = new ServerUnavailable();
        }
        return serverUnavailable;
    }

    public RecipeList getRecipeList() {
        return recipeList;
    }

}