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

import java.util.List;

import javafx.scene.layout.*;
import javafx.util.Callback;

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
    CheckComboBox<String> mealOptions;
    ObservableList<String> mealtypes;

    public RecipeList(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.setSpacing(5);
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #93c994;");
        mealtypes = FXCollections.observableArrayList();
        mealtypes.addAll(new String[] { "Breakfast", "Lunch", "Dinner" });

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
    }

    /*
     * Save tasks to a file called "Recipes.csv"
     */
    // TODO: Change it to update the recipe in mongo instead of csv
    public void saveRecipes() {
        try {
            File csvfile = new File("recipes.csv");
            FileWriter fw = new FileWriter(csvfile);
            for (int i = 0; i < this.getChildren().size(); i++) {
                if (this.getChildren().get(i) instanceof Recipe) {
                    Recipe Recipe = (Recipe) this.getChildren().get(i);
                    String name = Recipe.getName().getText();
                    String ingredients = Recipe.getIngredient().getText();
                    String instruction = Recipe.getInstruction().getText();
                    String mealType = Recipe.getMealType().getText();
                    fw.write(name + "-" + ingredients + "-" + instruction + "-" + mealType +
                            "\n");
                }
            }
            fw.close();
        } catch (Exception e) {
            System.out.println("savetasks() not implemented!");
        }
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
        // System.out.println(recipeList);
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

            this.getChildren().add(recipe);
        }
    }

    public void filterRecipes() {
        String[] selectedMealTypes = mealOptions.getCheckModel().getCheckedItems().toArray(new String[0]);
        for (Node node : this.getChildren()) {
            if (node instanceof Recipe) {
                Recipe recipe = (Recipe) node;
                String mealType = recipe.getMealType().getText().toLowerCase();
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