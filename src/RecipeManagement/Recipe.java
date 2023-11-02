package RecipeManagement;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.geometry.Insets;

public class Recipe extends HBox {

    private Label nameLabel;
    private VBox recipeInfo;
    private TextField name;
    private TextField mealType;
    private TextField ingredient;
    private TextField instruction;
    private Button uploadButton;
    private Button deleteButton;

    // String[] recipeDetails = new String[];

    public Recipe() {
        
        this.setPrefSize(500, 100); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task

        recipeInfo = new VBox();
        nameLabel = new Label("Recipe Name:");

        this.name = new TextField(); // create task name text field
        this.name.setPrefSize(200, 20); // set size of text field
        this.name.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        this.name.setAlignment(Pos.CENTER);
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.getChildren().add(nameLabel);
        

        this.mealType = new TextField();

        this.ingredient = new TextField();

        this.instruction = new TextField();

        // this.getChildren().addAll(this.name, this.mealType, this.ingredient, this.instruction);

        uploadButton = new Button("Upload"); // creates a button for marking the task as done
        uploadButton.setPrefSize(100, 20);
        uploadButton.setPrefHeight(Double.MAX_VALUE);
        uploadButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        deleteButton = new Button("Delete"); // creates a button for marking the task as done
        deleteButton.setPrefSize(100, 25);
        deleteButton.setPrefHeight(Double.MAX_VALUE);
        deleteButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        this.getChildren().addAll(uploadButton, recipeInfo, deleteButton);
    }

    public TextField getName() {
        return this.name;
    }

    public TextField getMealType() {
        return this.mealType;
    }

    public TextField getIngredient() {
        return this.mealType;
    }

    public TextField getInstruction() {
        return this.instruction;
    }

    public Button getUploadButton(){
        return this.uploadButton;
    }

    public Button getDeleteButton(){
        return this.deleteButton;
    }

    // Checks if it is a completed recipe (if createRecipe -> RecipePopup -> mealtype -> ingredients -> name -> instructions)
    public Boolean isComplete(){
        return this.name.getText() != null && this.mealType.getText() != null && this.ingredient.getText() != null && this.instruction.getText() != null;
    }
}