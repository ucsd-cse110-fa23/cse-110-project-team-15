package client.view;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import org.controlsfx.control.CheckComboBox;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class RecipeList extends VBox {
    Recipe[] list;
    AppFrame appFrame;
    private VBox filterDropdown;
    CheckComboBox<String> mealOptions;
    ObservableList<String> mealtypes;
    
    
    public RecipeList(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.setSpacing(5);
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #93c994;");
        mealtypes = FXCollections.observableArrayList();
        mealtypes.addAll(new String[] {"Breakfast", "Lunch", "Dinner"});
    
        mealOptions = new CheckComboBox<String>(mealtypes);
        
        filterDropdown = new VBox(mealOptions);
        filterDropdown.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll(filterDropdown);

        mealOptions.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            filterRecipes();
        });
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
                    String mealType = Recipe.getMealType().getText();
                    fw.write(name + "-" + ingredients + "-" + instruction + "-" + mealType + "\n");
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
                recipe.getMealType().setText(info[3]);
                this.getChildren().add(recipe);                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterRecipes() {
        String[] selectedMealTypes = mealOptions.getCheckModel().getCheckedItems().toArray(new String[0]);
        for (Node node : this.getChildren()) {
            if (node instanceof Recipe) {
                Recipe recipe = (Recipe) node;
                String mealType = recipe.getMealType().getText().toLowerCase();
                System.out.println(recipe.getIngredient().getText());
                System.out.println(mealType);
                boolean shouldShow = selectedMealTypes.length == 0 || ifContains(selectedMealTypes, mealType);
                node.setVisible(shouldShow);
            }
        }
    }

    private boolean ifContains(String[] array, String value) {
        for (String str : array) {
            if (str.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}