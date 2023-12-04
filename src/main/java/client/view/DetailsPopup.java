package client.view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.net.URISyntaxException;
import java.net.URL;

//import com.google.common.io.Files;
import client.model.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DetailsPopup extends Stage {

    private Recipe recipe;

    private TextField name;
    private TextField ingredients;
    private TextArea instruction;

    private ImageView recipeImage;

    private Button editButton;
    private Button deleteButton;
    private Button saveButton;
    private Button backButton;
    private Button refreshButton;


    public DetailsPopup() {
        // setTitle(name.getText());
        setWidth(525);
        setHeight(650);
        // Create controls for the popup window
        name = new TextField();
        this.name.setPrefSize(200, 10); // set size of text field
        this.name.setStyle(
                " -fx-background-color: #659966;  -fx-font-weight: bold; -fx-font-size: 17; -fx-font-family: 'Times New Roman';");
        this.name.setAlignment(Pos.CENTER);
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        name.setEditable(false);

        ingredients = new TextField();
        ingredients.setPrefSize(50, 10); // set size of text field
        ingredients.setStyle(" -fx-font-weight: bold; -fx-font-size: 14; -fx-font-family: 'Times New Roman';");
        ingredients.setEditable(false);

        instruction = new TextArea();
        this.instruction.setPrefSize(200, 100); // set size of text field
        instruction.setStyle(" -fx-font-weight: bold; -fx-font-size: 14; -fx-font-family: 'Times New Roman';");
        this.instruction.setEditable(false);
        this.instruction.setWrapText(true);

        String defaultButtonStyle = ("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Times New Roman';");

        backButton = new Button("<-");
        backButton.setStyle(
                "-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Arial';");
        editButton = new Button("Edit");
        editButton.setStyle(defaultButtonStyle);
        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle);
        deleteButton = new Button("Delete");
        deleteButton.setStyle(defaultButtonStyle);
        refreshButton = new Button("Refresh");
        refreshButton.setStyle(defaultButtonStyle);

        Image defaultImage = new Image("https://oaidalleapiprodscus.blob.core.windows.net/private/org-Sd9bwBmEf5IDns4KIh3k3fXp/user-DgtoVZso5Yqo57hZ4b0PreAg/img-J5bN9XvbTsOKfNxM4jZ5nZeA.png?st=2023-12-04T00%3A19%3A56Z&se=2023-12-04T02%3A19%3A56Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/png&skoid=6aaadede-4fb3-4698-a8f6-684d7786b067&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2023-12-03T22%3A44%3A30Z&ske=2023-12-04T22%3A44%3A30Z&sks=b&skv=2021-08-06&sig=xfmsjhcQXD23/A8KzCCNsB/Q0tJ5h3IknRwQNI7hnLo%3D");
        recipeImage = new ImageView(defaultImage);
        recipeImage.setFitWidth(200);
        recipeImage.setFitHeight(200);

        // Create a layout for the controls
        VBox layout = new VBox(10); // 10 pixels spacing
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(recipeImage, name, ingredients, instruction, backButton, editButton, deleteButton, saveButton,
                refreshButton);

        // Add an action for the "Add" button
        saveButton.setOnAction(e -> {
            recipe.getName().setText(name.getText());
            recipe.getIngredient().setText(ingredients.getText());
            recipe.getInstruction().setText(instruction.getText());
            recipe.saveRecipe();
            close(); // Close the popup window
        });

        editButton.setOnAction(e -> {
            ingredients.setEditable(!ingredients.isEditable());
            instruction.setEditable(!instruction.isEditable());
        });

        // Add an action for the "Delete" button
        deleteButton.setOnAction(e -> {
            recipe.deleteRecipe();
            recipe.saveRecipe();
            close();
        });

        backButton.setOnAction(e -> {
            close();
        });

        // Create a scene and set it for the popup window
        Scene scene = new Scene(layout);
        setScene(scene);
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) throws IOException, InterruptedException, URISyntaxException{
        this.recipe = recipe;
        name.setText(recipe.getName().getText());
        ingredients.setText(recipe.getIngredient().getText());
        instruction.setText(recipe.getInstruction().getText());
        String url = recipe.getImageURL().getText();
        Image image = new Image(url);
        recipeImage.setImage(image);
    }

    public Button getEditButton() {
        return this.editButton;
    }

    public Button getDeleteButton() {
        return this.deleteButton;
    }

    public Button getSaveButton() {
        return this.saveButton;
    }

    public Button getRefreshButton() {
        return this.refreshButton;
    }

    public void setRefreshButtonAction(EventHandler<ActionEvent> eventHandler) {
        refreshButton.setOnAction(eventHandler);
    }
}