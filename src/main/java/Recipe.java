import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.util.Map;

//import RecipeManagement.RecipePopup.DetailsPopup;

import java.util.HashMap;

public class Recipe extends HBox {

    private Label nameLabel;
    private VBox recipeInfo;
    private TextField name;
    private TextField mealType;
    private TextField ingredient;
    private TextField instruction;
    private Button uploadButton;
    private Button deleteButton;
    private Button detailButton;
    public Map<String, String[]> recipe = new HashMap<>();
    String[] recipeDetails = new String[3];

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
        detailButton = new Button("View");
        detailButton.setPrefSize(100, 20);
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle("-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;");
        this.getChildren().addAll(nameLabel, name, detailButton);
        

        this.mealType = new TextField();

        this.ingredient = new TextField();

        this.instruction = new TextField();

        recipeDetails[0] = mealType.toString();
        recipeDetails[1] = ingredient.toString();
        recipeDetails[2] = instruction.toString();
        recipe.put(name.toString(), recipeDetails);

        // this.getChildren().addAll(this.name, this.mealType, this.ingredient, this.instruction);

        /*uploadButton = new Button("Upload"); // creates a button for marking the task as done
        uploadButton.setPrefSize(100, 20);
        uploadButton.setPrefHeight(Double.MAX_VALUE);
        uploadButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        deleteButton = new Button("Delete"); // creates a button for marking the task as done
        deleteButton.setPrefSize(100, 25);
        deleteButton.setPrefHeight(Double.MAX_VALUE);
        deleteButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button*/

        this.getChildren().addAll(recipeInfo);
        addListeners();
    }

    public TextField getName() {
        return this.name;
    }

    public TextField getMealType() {
        return this.mealType;
    }

    public TextField getIngredient() {
        return this.ingredient;
    }

    public TextField getInstruction() {
        return this.instruction;
    }

    public Button getDetailButton() {
        return this.detailButton;
    }

    // public RecipeDetails getDetails() {
    //     return new RecipeDetails(name.getText(), mealType.getText(), ingredient.getText(), instruction.getText());
    // }

    public void addListeners() 
    {
        detailButton.setOnAction(e -> {
            DetailsPopup popup = new DetailsPopup(this);
            popup.showAndWait();
        });
    }

    /*public Button getUploadButton(){
        return this.uploadButton;
    }

    public Button getDeleteButton(){
        return this.deleteButton;
    }*/

    // Checks if it is a completed recipe (if createRecipe -> RecipePopup -> mealtype -> ingredients -> name -> instructions)
    public Boolean isComplete(){
        return this.name.getText() != null && this.mealType.getText() != null && this.ingredient.getText() != null && this.instruction.getText() != null;
    }
}