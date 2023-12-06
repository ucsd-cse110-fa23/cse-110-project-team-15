package client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import java.util.Map;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TextField imageURL;
    private String id;
    private Button detailButton;
    public Map<String, String[]> recipe = new HashMap<>();
    String[] recipeDetails = new String[4];

    public Recipe(AppFrame appframe) {

        this.recipeList = appframe.getRecipeList();
        this.detailsPopup = appframe.getDetailsPopup();

        this.setPrefSize(600, 50); // sets size of task
        this.setStyle(
                "-fx-background-color: #659966; -fx-border-width: 0; -fx-font-weight: bold; -fx-font-size: 11; -fx-font-family: 'Times New Roman';");

        recipeInfo = new VBox();
        // nameLabel = new Label("Recipe Name:");

        this.name = new TextField(); // create task name text field
        this.name.setPrefSize(200, 10); // set size of text field
        this.name.setStyle(
                "-fx-background-color: #659966; -fx-border-width: 0; -fx-font-size: 17; -fx-font-family: 'Times New Roman';");
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.name.setAlignment(Pos.CENTER_LEFT);
        this.name.setEditable(false);

        this.mealType = new TextField();
        this.mealType.setPrefSize(100, 10); // set size of text field
        this.mealType.setStyle(
                "-fx-background-color: #659966; -fx-border-width: 0; -fx-font-size: 17; -fx-font-family: 'Times New Roman';");
        this.mealType.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        this.mealType.setAlignment(Pos.CENTER_LEFT);
        this.mealType.setEditable(false);

        detailButton = new Button("View");
        detailButton.setPrefSize(50, 10);
        detailButton.setPrefHeight(Double.MAX_VALUE);
        detailButton.setStyle(
                " -fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Times New Roman';");
        this.setPadding(new Insets(0, 0, 0, 100));

        HBox recipecontainer = new HBox();
        recipecontainer.setAlignment(Pos.CENTER);
        recipecontainer.setSpacing(50);
        recipecontainer.getChildren().addAll(mealType, name, detailButton);
        this.getChildren().addAll(recipecontainer);

        // this.mealType = new TextField();
        this.ingredient = new TextField();
        this.instruction = new TextField();
        this.imageURL = new TextField();

        recipeDetails[0] = mealType.toString();
        recipeDetails[1] = ingredient.toString();
        recipeDetails[2] = instruction.toString();
        recipeDetails[3] = imageURL.toString();
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

    public TextField getImageURL() {
        return this.imageURL;
    }

    public Button getDetailButton() {
        return this.detailButton;
    }

    public String getRecipeId() {
        return this.id;
    }

    public LocalDateTime getRecipeIdAsDateTime() {
        String recipeId = this.id;
        System.out.println(recipeId);
        return LocalDateTime.parse(recipeId, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public void setRecipeId(String id) {
        this.id = id;
    }

    public void addListeners() {
        detailButton.setOnAction(e -> {
            try {
                detailsPopup.setRecipe(this);
            } catch (IOException | InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            detailsPopup.show();
        });
    }

    // Checks if it is a completed recipe (if createRecipe -> RecipePopup ->
    // mealtype -> ingredients -> name -> instructions)
    public Boolean isComplete() {
        return !this.name.getText().isEmpty() && !this.mealType.getText().isEmpty()
                && !this.ingredient.getText().isEmpty() && !this.instruction.getText().isEmpty()
                && !this.imageURL.getText().isEmpty();
    }

    public void deleteRecipe() {
        // Remove this recipe from the RecipeList
        recipeList.removeRecipe(this);
    }

    // public void saveRecipe() {
    // recipeList.saveRecipes();
    // }

    public void addRecipe() {
        if (isComplete())
            recipeList.addRecipe(this);
        else
            System.err.println("Failed to add recipe: Recipe incomplete");
    }

    public String getNameText() {
        return this.name.getText();
    }
}