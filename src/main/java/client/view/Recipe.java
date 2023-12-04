package client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.util.Map;

import java.sql.Timestamp;

//import RecipeManagement.RecipePopup.DetailsPopup;

import java.util.HashMap;

public class Recipe extends HBox {

    // Recipe should not have recipelist
    private RecipeList recipeList;
    private DetailsPopup detailsPopup;

    // private Label nameLabel;
    private VBox recipeInfo;
    private TextField name;
    private TextField mealType;
    private TextField ingredient;
    private TextField instruction;
    private Timestamp id;
    private Button detailButton;
    public Map<String, String[]> recipe = new HashMap<>();

    public Recipe(AppFrame appframe) {

        this.recipeList = appframe.getRecipeList();
        this.detailsPopup = appframe.getDetailsPopup();

        this.setPrefSize(500, 50); // sets size of task
        this.setStyle( "-fx-background-color: #659966; -fx-border-width: 0; -fx-font-weight: bold; -fx-font-size: 11; -fx-font-family: 'Times New Roman';"); 

        recipeInfo = new VBox();
        // nameLabel = new Label("Recipe Name:");

        this.name = new TextField(); // create task name text field
        this.name.setPrefSize(200, 10); // set size of text field
        this.name.setStyle( "-fx-background-color: #659966; -fx-border-width: 0; -fx-font-size: 17; -fx-font-family: 'Times New Roman';"); 
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.name.setAlignment(Pos.CENTER_LEFT);
        this.name.setEditable(false);

        this.mealType = new TextField();
        this.mealType.setPrefSize(100, 10); // set size of text field
        this.mealType.setStyle( "-fx-background-color: #659966; -fx-border-width: 0; -fx-font-size: 17; -fx-font-family: 'Times New Roman';"); 
        this.mealType.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.mealType.setAlignment(Pos.CENTER_LEFT);
        this.mealType.setEditable(false);

        detailButton = new Button("View");
        detailButton.setPrefSize(50, 10);
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setAlignment(Pos.CENTER_RIGHT);
        detailButton.setStyle(
                " -fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Times New Roman';");
        this.setPadding(new Insets(0, 0, 0, 20));

        HBox recipecontainer = new HBox();
        recipecontainer.setAlignment(Pos.CENTER);
        recipecontainer.setSpacing(50);
        recipecontainer.getChildren().addAll(mealType, name, detailButton);
        this.getChildren().addAll(recipecontainer);

        
        this.ingredient = new TextField();
        this.instruction = new TextField();

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

    public Timestamp getRecipeId() {
        return this.id;
    }

    public Timestamp setRecipeId(Timestamp id) {
        return this.id = id;
    }

    public void addListeners() {
        detailButton.setOnAction(e -> {
            detailsPopup.setRecipe(this);
            detailsPopup.show();
        });
    }

    // Checks if it is a completed recipe (if createRecipe -> RecipePopup ->
    // mealtype -> ingredients -> name -> instructions)
    public Boolean isComplete() {
        return !this.name.getText().isEmpty() && !this.mealType.getText().isEmpty()
                && !this.ingredient.getText().isEmpty() && !this.instruction.getText().isEmpty();
    }

    public void deleteRecipe() {
        // Remove this recipe from the RecipeList
        recipeList.removeRecipe(this);
    }

    public void saveRecipe() {
        recipeList.saveRecipes();
    }

    public void addRecipe() {
        if (isComplete())
            recipeList.addRecipe(this);
        else
            System.err.println("Failed to add recipe: Recipe incomplete");
    }
}