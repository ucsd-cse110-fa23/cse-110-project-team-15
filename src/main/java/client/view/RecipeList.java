package client.view;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class RecipeList extends VBox {
    Recipe[] list;
    AppFrame appFrame;
    private VBox filterDropdown;
    ComboBox<CheckBox> comboBox;
    
    public RecipeList(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.setSpacing(5); // sets spacing between tasks
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #93c994;");

        comboBox = new ComboBox<>();
        comboBox.setPromptText("Filter By Meal Type");

        comboBox.getItems().addAll(
            new CheckBox("BreakFast"),
            new CheckBox("Lunch"),
            new CheckBox("Dinner")
        );
        
        filterDropdown = new VBox(comboBox);
        filterDropdown.setAlignment(Pos.CENTER_RIGHT);
        filterDropdown.setStyle("-fx-background-color: #93c994;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");
        this.getChildren().addAll(filterDropdown);

        // filterButton = new Button("Filter By Meal Type");
        // filterButton.setStyle("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");
    }

    public void removeRecipe(Recipe Recipe){
        this.getChildren().remove(Recipe);
    }

    /*
     * Save tasks to a file called "Recipes.csv"
     */
    public void saveRecipes() {
        try{
            File csvfile = new File("recipes.csv");
            FileWriter fw = new FileWriter(csvfile);
            for (int i = 0; i < this.getChildren().size(); i++){
                if (this.getChildren().get(i) instanceof Recipe){
                    Recipe Recipe = (Recipe) this.getChildren().get(i);
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
        }
        catch(Exception e){
            System.out.println("sorttasks() not implemented!");
        }
    }

    public void loadTasks() {

        try (BufferedReader reader = new BufferedReader(new FileReader("recipes.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split("-");
                Recipe recipe = new Recipe(appFrame);
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