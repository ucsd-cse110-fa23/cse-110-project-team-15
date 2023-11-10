
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.*;
import java.util.Map;
import java.util.HashMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


class RecipeList extends VBox {
    Recipe[] list;


    RecipeList() {
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #93c994;");
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
                    String name = Recipe.getName().getText();
                    String ingredients = Recipe.getIngredient().getText();
                    String instruction = Recipe.getInstruction().getText();
                    fw.write(name + "-" + ingredients + "-" + instruction + "\n");
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
                    list.add(Recipe.getName().getText());
                    clist.add(Recipe);
                    index++;
                }
            }
            Collections.sort(list);
            for (String recipeName: list){
                for (Recipe Recipe: clist){
                    if (Recipe.getName().getText().equals(recipeName)){
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

    public void loadTasks() {

        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RecipeList list = new RecipeList();
                String[] info = line.split("-");
                Recipe recipe = new Recipe(list);
                recipe.getName().setText(info[0]);
                recipe.getIngredient().setText(info[1]);
                recipe.getInstruction().setText(info[2]);
                this.getChildren().add(recipe);                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

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

class AppFrame extends BorderPane {

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

        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split("-");
                Recipe recipe = new Recipe(RecipeList);
                recipe.getName().setText(info[0]);
                recipe.getIngredient().setText(info[1]);
                recipe.getInstruction().setText(info[2]);
                RecipeList.getChildren().add(recipe);                
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
            Recipe recipe = new Recipe(RecipeList);
            RecipeList.getChildren().add(recipe);
            Whisper whisp = new Whisper();
            ChatGPT gpt = new ChatGPT();
            RecipePopup popup = new RecipePopup(recipe, whisp, gpt);
            popup.display();
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
