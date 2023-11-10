import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.util.Map;

//import RecipeManagement.RecipePopup.DetailsPopup;

import java.util.HashMap;

public class Recipe extends HBox {

    private RecipeList recipeList;

    // private Label nameLabel;
    private VBox recipeInfo;
    private TextField name;
    private TextField mealType;
    private TextField ingredient;
    private TextField instruction;
    private Button detailButton;
    public Map<String, String[]> recipe = new HashMap<>();
    String[] recipeDetails = new String[3];

    public Recipe(RecipeList recipeList) {

        this.recipeList = recipeList;
        
        this.setPrefSize(500, 50); // sets size of task
        this.setStyle("-fx-background-color: #659966; -fx-border-width: 0; -fx-font-weight: bold; -fx-font-size: 11; -fx-font-family: 'Times New Roman';"); // sets background color of task

        recipeInfo = new VBox();
        // nameLabel = new Label("Recipe Name:");

        this.name = new TextField(); // create task name text field
        this.name.setPrefSize(200, 10); // set size of text field
        this.name.setStyle("-fx-background-color: #659966; -fx-border-width: 0; -fx-font-size: 17; -fx-font-family: 'Times New Roman';"); // set background color of texfield
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.name.setAlignment(Pos.CENTER_LEFT);
        this.name.setEditable(false);

        detailButton = new Button("View");
        detailButton.setPrefSize(50, 10);
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle(" -fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Times New Roman';");
        this.setPadding(new Insets(0, 0, 0, 100)); 

        HBox recipecontainer = new HBox();
        recipecontainer.setAlignment(Pos.CENTER);
        recipecontainer.setSpacing(50);
        recipecontainer.getChildren().addAll(name, detailButton);
        this.getChildren().addAll(recipecontainer);

        this.mealType = new TextField();

        this.ingredient = new TextField();

        this.instruction = new TextField();

        recipeDetails[0] = mealType.toString();
        recipeDetails[1] = ingredient.toString();
        recipeDetails[2] = instruction.toString();
        recipe.put(name.toString(), recipeDetails);

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

    public void addListeners() 
    {
        detailButton.setOnAction(e -> {
            DetailsPopup popup = new DetailsPopup(this);
            popup.show();
        });
    }

    // Checks if it is a completed recipe (if createRecipe -> RecipePopup -> mealtype -> ingredients -> name -> instructions)
    public Boolean isComplete(){
        return this.name.getText() != null && this.mealType.getText() != null && this.ingredient.getText() != null && this.instruction.getText() != null;
    }

    public void deleteRecipe() {
        // Remove this recipe from the RecipeList
        recipeList.removeRecipe(this);
    }

    public void saveRecipe() {
        recipeList.saveRecipes();
    }
}