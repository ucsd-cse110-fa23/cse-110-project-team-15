package src.RecipeManagement;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.util.Map;
import java.util.HashMap;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import src.RecipeManagement.RecipePopup.RecipePopup;

import java.util.*; 

class Recipe extends HBox {

    private VBox recipeInfo;
    private TextField recipeName;
    private Button uploadButton;
    private Button deleteButton;
    private Label nameLabel;

    // String[] recipeDetails = new String[];

    Recipe() {
        this.setPrefSize(500, 100); // sets size of task
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of task

        recipeInfo = new VBox();
        nameLabel = new Label("Recipe Name:");

        recipeName = new TextField(); // create task name text field
        recipeName.setPrefSize(200, 20); // set size of text field
        recipeName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // set background color of texfield
        recipeName.setAlignment(Pos.CENTER);
        recipeName.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        recipeInfo.getChildren().add(nameLabel);
        recipeInfo.getChildren().add(recipeName); // add textlabel to task

        uploadButton = new Button("Upload"); // creates a button for marking the task as done
        uploadButton.setPrefSize(100, 20);
        uploadButton.setPrefHeight(Double.MAX_VALUE);
        uploadButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button

        deleteButton = new Button("Delete"); // creates a button for marking the task as done
        deleteButton.setPrefSize(100, 25);
        deleteButton.setPrefHeight(Double.MAX_VALUE);
        deleteButton.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0;"); // sets style of button


        // uploadButton.setOnAction(e -> uploadImage());

        this.getChildren().addAll(uploadButton, recipeInfo, deleteButton);

    }

    public TextField getrecipeName() {
        return this.recipeName;
    }
    public Button getUploadButton(){
        return this.uploadButton;
    }

    public Button getDeleteButton(){
        return this.deleteButton;
    }
}

class RecipeList extends VBox {
    Map<String, String[]> recipes = new HashMap<>();

    RecipeList() {
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
    }

    public void removeRecipe(Recipe Recipe){
        this.getChildren().remove(Recipe);
    }

    public void updateRecipeIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Recipe) {
                index++;
            }
        }
    }

    // public void recipeMapping() {
    //     String[] recipeDetails = {}
    //  }

    /*
     * Save tasks to a file called "Recipes.csv"
     */
    public void saveRecipes() {
        try{
            File csvfile = new File("recipes.csv");
            FileWriter fw = new FileWriter(csvfile);
            int index = 1;
            for (int i = 0; i < this.getChildren().size(); i++){
                if (this.getChildren().get(i) instanceof Recipe){
                    Recipe Recipe = (Recipe) this.getChildren().get(i);
                    index++;
                    String details = Recipe.getrecipeName().getText();
                    fw.write(details + "\n");
                }
            }
            fw.close();
        }
        catch(Exception e){
            System.out.println("savetasks() not implemented!");
    
        }
    }

    /*
     * Sort the Recipes lexicographically
     */
    public void sortRecipes() {
        try{
            ArrayList <String> list = new ArrayList<String>();
            ArrayList <Recipe> clist = new ArrayList<Recipe>();
            ArrayList <Recipe> clist2 = new ArrayList<Recipe> ();
            int index = 1;
            for (int i = 0; i < this.getChildren().size(); i++){
                if (this.getChildren().get(i) instanceof Recipe){
                    Recipe Recipe = (Recipe) this.getChildren().get(i);
                    list.add(Recipe.getrecipeName().getText());
                    clist.add(Recipe);
                    index++;
                }
            }
            Collections.sort(list);
            for (String recipeName: list){
                for (Recipe Recipe: clist){
                    if (Recipe.getrecipeName().getText().equals(recipeName)){
                        clist2.add(Recipe);
                    }
                }
            }
            this.getChildren().clear();
            this.getChildren().addAll(clist2);
            updateRecipeIndices();
        }
        catch(Exception e){
            System.out.println("sorttasks() not implemented!");
        }
    }

    /*
     * Load Recipes from a file called "Recipes.csv"
     */
    public void loadRecipes() {
        try{
            BufferedReader in = new BufferedReader(new FileReader("Recipes.csv"));
            String splitter = ",";
            String line = in.readLine();
            while (line != null){
                Recipe Recipe = new Recipe();
                String[] detail = line.split(splitter);
                Recipe.getrecipeName().setText(detail[0]);
                this.getChildren().add(Recipe);
                // Button doneButton = task.getDoneButton();
                // doneButton.setOnAction(e -> {
                //     task.toggleDone();
                // });
                line = in.readLine();
            }
            in.close();
            this.updateRecipeIndices();
        }
        catch(Exception e){
            System.out.println("loadtasks() not implemented!");
        }
        
    }
}

class Footer extends HBox {

    private Button createButton;

    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

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
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Recipe Management App"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center

        
    }
}

class AppFrame extends BorderPane{

    private Header header;
    private Footer footer;
    private RecipeList RecipeList;

    private Button createButton;

    AppFrame()
    {
        // Initialise the header Object
        header = new Header();

        // Create a RecipeList Object to hold the tasks
        RecipeList = new RecipeList();
        
        // Initialise the Footer Object
        footer = new Footer();

        // Add a Scroller to the Task List
        ScrollPane scroll = new ScrollPane(RecipeList);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroll);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);

        createButton = footer.getCreateButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {

        createButton.setOnAction(e -> {
            RecipePopup popup = new RecipePopup();
            popup.show();
        });
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the RecipeList
        AppFrame root = new AppFrame();

        // Set the title of the app
        primaryStage.setTitle("Recipe Management App");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
