import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


import javafx.geometry.Pos;
// import java.io.IOException;
// import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;

public class DetailsPopup extends Stage {

    private Recipe recipe;

    private TextField name;
    private TextField ingredients;
    private TextField instruction;
    
    private Button editButton;
    private Button deleteButton;
    private Button saveButton;
    private Button backButton;
    private HBox buttonBox;
    private VBox layout;

    public DetailsPopup(Recipe recipe) {
        this.recipe = recipe;
        // setTitle(name.getText());
        setWidth(300);
        setHeight(200);

        // Create controls for the popup window
        name = new TextField(recipe.getName().getText());
        name.setEditable(false);
        ingredients = new TextField(recipe.getIngredient().getText());
        ingredients.setEditable(false);
        instruction = new TextField(recipe.getInstruction().getText());
        instruction.setEditable(false);

        backButton = new Button("<-");
        editButton = new Button("Edit");
        saveButton = new Button("Save");
        deleteButton = new Button("Delete");

        // Create a layout for the controls
        VBox layout = new VBox(10); // 10 pixels spacing
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(name, ingredients, instruction, backButton, editButton, deleteButton, saveButton);

        // Add an action for the "Add" button
        saveButton.setOnAction(e -> {
            recipe.getName().setText(name.getText());
            recipe.getIngredient().setText(ingredients.getText());
            recipe.getInstruction().setText(instruction.getText());
            close(); // Close the popup window
            });

        editButton.setOnAction(e -> {
            ingredients.setEditable(!ingredients.isEditable());
            instruction.setEditable(!instruction.isEditable());
            });

        // Add an action for the "Delete" button
        deleteButton.setOnAction(e -> {
            recipe.deleteRecipe();
            close();
        });

        // Create a scene and set it for the popup window
        Scene scene = new Scene(layout);
        setScene(scene);
    }
}