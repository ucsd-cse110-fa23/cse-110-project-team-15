package client.view;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.*;

import java.io.*;

class Footer extends HBox {

    private Button createButton;
    

    Footer() {
        this.setPrefSize(500, 60);
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

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #93c994;");

        Text titleText = new Text("Recipe Creation App"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold;  -fx-font-size: 25; -fx-font-family: 'Lucida Bright';");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

public class AppFrame extends BorderPane {

    private Header header;
    private Footer footer;
    private RecipeList recipeList;
    private RecipePopup recipePopup;
    private DetailsPopup detailsPopup;

    private Button createButton;

    public AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        recipeList = new RecipeList(this);
        
        // Initialise the Footer Object
        footer = new Footer();

        recipePopup = new RecipePopup();
        detailsPopup = new DetailsPopup();

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

        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split("-");
                Recipe recipe = new Recipe(this);
                recipe.getName().setText(info[0]);
                recipe.getIngredient().setText(info[1]);
                recipe.getInstruction().setText(info[2]);
                recipeList.getChildren().add(recipe);                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners() 
    {
        createButton.setOnAction(e -> {
            Recipe recipe = new Recipe(this);
            recipeList.getChildren().add(recipe);
            recipePopup.setRecipe(recipe);
            recipePopup.display();
        });
    }

    public RecipeList getRecipes() {
        return recipeList;
    }

    public RecipePopup getRecipePopup() {
        return recipePopup;
    }

    public DetailsPopup getDetailsPopup() {
        return detailsPopup;
    }

    public RecipeList getRecipeList() {
        return recipeList;
    }
}
