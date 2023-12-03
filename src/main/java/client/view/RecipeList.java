package client.view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import org.controlsfx.control.CheckComboBox;

import java.util.List;

import javafx.scene.layout.*;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;

import org.bson.Document;

public class RecipeList extends VBox {
    Recipe[] list;
    AppFrame appFrame;
    private VBox filterDropdown;
    public CheckComboBox<String> mealOptions;
    ObservableList<String> mealtypes;
    private ArrayList<Recipe> recipeContainer;

    public RecipeList(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.setSpacing(5);
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #93c994;");
        mealtypes = FXCollections.observableArrayList();
        mealtypes.addAll(new String[] { "Breakfast", "Lunch", "Dinner" });

        recipeContainer = new ArrayList<Recipe>();

        mealOptions = new CheckComboBox<String>(mealtypes);

        filterDropdown = new VBox(mealOptions);
        filterDropdown.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll(filterDropdown);

        mealOptions.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            filterRecipes();
        });
    }

    public void removeRecipe(Recipe Recipe) {
        this.getChildren().remove(Recipe);
        recipeContainer.remove(Recipe);
        filterRecipes();
    }

    public void clearRecipes() {
        ArrayList<Node> filterAndOtherNodes = new ArrayList<>();
        for (Node node : getChildren()) {
            if (node == filterDropdown) {
                filterAndOtherNodes.add(node);
            }
        }
        getChildren().clear();
        getChildren().addAll(filterAndOtherNodes);
        recipeContainer.clear();
    }

    /*
     * Save tasks to a file called "Recipes.csv". DO NOT DO THIS ON UI
     */
    // TODO: Change it to update the recipe in mongo instead of csv
    public void saveRecipes() {
        for (int i = 0; i < recipeContainer.size(); i++) {
                Recipe Recipe = recipeContainer.get(i);
                String name = Recipe.getName().getText();
                String ingredients = Recipe.getIngredient().getText();
                String instruction = Recipe.getInstruction().getText();
                String mealType = Recipe.getMealType().getText();
        }
        String id = server.Login.getID();
        server.UpdateRecipes(id, name, ingredients, )
        /**try {
            File csvfile = new File("recipes.csv");
            FileWriter fw = new FileWriter(csvfile);
            for (int i = 0; i < recipeContainer.size(); i++) {
                Recipe Recipe = recipeContainer.get(i);
                String name = Recipe.getName().getText();
                String ingredients = Recipe.getIngredient().getText();
                String instruction = Recipe.getInstruction().getText();
                String mealType = Recipe.getMealType().getText();
                fw.write(name + "-" + ingredients + "-" + instruction + "-" + mealType + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("savetasks() not implemented!");
        }**/
    }

    /*
     * Sort the Recipes lexicographically
     */
    public void sortRecipes() {
        try {
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<Recipe> clist = new ArrayList<Recipe>();
            ArrayList<Recipe> clist2 = new ArrayList<Recipe>();
            int index = 1;
            for (int i = 0; i < this.getChildren().size(); i++) {
                if (this.getChildren().get(i) instanceof Recipe) {
                    Recipe Recipe = (Recipe) this.getChildren().get(i);
                    list.add(Recipe.getName().getText());
                    clist.add(Recipe);
                    index++;
                }
            }
            Collections.sort(list);
            for (String recipeName : list) {
                for (Recipe Recipe : clist) {
                    if (Recipe.getName().getText().equals(recipeName)) {
                        clist2.add(Recipe);
                    }
                }
            }
            this.getChildren().clear();
            this.getChildren().addAll(clist2);
        } catch (Exception e) {
            System.out.println("sorttasks() not implemented!");
        }
    }

    // loadTasks from Mongo
    public void loadTasks() {
        List<Document> recipeList = server.LoadRecipes.loadRecipes(server.Login.getID());
        for (Document recipeDoc : recipeList) {
            Recipe recipe = new Recipe(appFrame);

            String recipeName = recipeDoc.getString("recipeName");
            String recipeIngredients = recipeDoc.getString("recipeIngredients");
            String recipeInstructions = recipeDoc.getString("recipeInstructions");
            String mealType = recipeDoc.getString("mealType");

            recipe.getName().setText(recipeName);
            recipe.getIngredient().setText(recipeIngredients);
            recipe.getInstruction().setText(recipeInstructions);
            recipe.getMealType().setText(mealType);
            recipeContainer.add(recipe);
            this.getChildren().add(recipe);
        }
    }

    public void filterRecipes() {
        String[] selectedMealTypes = mealOptions.getCheckModel().getCheckedItems().toArray(new String[0]);

        ArrayList<Node> list = new ArrayList<Node>();
        for (Node node : this.getChildren()) {
            if (node instanceof Recipe)
                continue;
            list.add(node);
        }

        for (Recipe recipe : recipeContainer) {
            String mealType = recipe.getMealType().getText().toLowerCase();
            System.out.println(mealType);
            if (selectedMealTypes.length == 0 || ifContains(selectedMealTypes, mealType)) {
                list.add(recipe);
            }
        }
        this.getChildren().clear();
        this.getChildren().addAll(list);
    }

    private boolean ifContains(String[] array, String value) {
        for (String str : array) {
            if (str.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipeContainer;
    }

    public void addRecipe(Recipe recipe) {
        recipeContainer.add(recipe);
        filterRecipes();
    }
}