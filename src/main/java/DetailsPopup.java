import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javafx.geometry.Insets;
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
    private TextArea instruction;
    
    private Button editButton;
    private Button deleteButton;
    private Button saveButton;
    private Button backButton;
    private HBox buttonBox;
    private VBox layout;

    public DetailsPopup(Recipe recipe) {
        this.recipe = recipe;
        // setTitle(name.getText());
        setWidth(525);
        setHeight(650);

        // Create controls for the popup window
        name = new TextField(recipe.getName().getText());
        this.name.setPrefSize(200, 10); // set size of text field
        this.name.setStyle(" -fx-background-color: #659966;  -fx-font-weight: bold; -fx-font-size: 17; -fx-font-family: 'Times New Roman';");
        this.name.setAlignment(Pos.CENTER);
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        name.setEditable(false);

        ingredients = new TextField(recipe.getIngredient().getText());
        ingredients.setPrefSize(50, 10); // set size of text field
        ingredients.setStyle(" -fx-font-weight: bold; -fx-font-size: 14; -fx-font-family: 'Times New Roman';");
        ingredients.setEditable(false);

        instruction = new TextArea(recipe.getInstruction().getText());
        this.instruction.setPrefSize(200, 100); // set size of text field
        instruction.setStyle(" -fx-font-weight: bold; -fx-font-size: 14; -fx-font-family: 'Times New Roman';");
        this.instruction.setEditable(false);
        this.instruction.setWrapText(true);

        String defaultButtonStyle = ("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Times New Roman';");

        backButton = new Button("<-");
        backButton.setStyle("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Arial';"); 
        editButton = new Button("Edit");
        editButton.setStyle(defaultButtonStyle); 
        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle); 
        deleteButton = new Button("Delete");
        deleteButton.setStyle(defaultButtonStyle); 

        // Create a layout for the controls
        VBox layout = new VBox(10); // 10 pixels spacing
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(name, ingredients, instruction, backButton, editButton, deleteButton, saveButton);

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
}