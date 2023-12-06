package client.view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import org.controlsfx.control.CheckComboBox;

import java.util.List;

import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.Collections;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.Document;

public class RecipeList extends VBox {
    Recipe[] list;
    AppFrame appFrame;
    private VBox filterDropdown;
    public CheckComboBox<String> mealOptions;
    ObservableList<String> mealtypes;

    private ArrayList<Recipe> recipeContainer;

    private VBox filterTimeDropdown;
    public CheckComboBox<String> timeOptions;
    ObservableList<String> timeTypes;

    private VBox filterSortDropdown;
    public CheckComboBox<String> sortOptions;
    ObservableList<String> sortTypes;

    public RecipeList(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.setSpacing(5);
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #93c994;");

        recipeContainer = new ArrayList<Recipe>();

        mealtypes = FXCollections.observableArrayList();
        mealtypes.addAll(new String[] { "Breakfast", "Lunch", "Dinner" });
        mealOptions = new CheckComboBox<String>(mealtypes);
        filterDropdown = new VBox(mealOptions);
        filterDropdown.setAlignment(Pos.CENTER_RIGHT);

        timeTypes = FXCollections.observableArrayList();
        timeTypes.addAll(new String[] { "Newest", "Oldest" });
        timeOptions = new CheckComboBox<String>(timeTypes);
        filterTimeDropdown = new VBox(timeOptions);
        filterTimeDropdown.setAlignment(Pos.CENTER_RIGHT);

        sortTypes = FXCollections.observableArrayList();
        sortTypes.addAll(new String[] { "A-Z", "Z-A" });
        sortOptions = new CheckComboBox<String>(sortTypes);
        filterSortDropdown = new VBox(sortOptions);
        filterSortDropdown.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(filterDropdown, filterTimeDropdown, filterSortDropdown);

        mealOptions.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            filterRecipes();
        });

        timeOptions.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            sortRecipes();
        });

        sortOptions.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends String> c) -> {
            sortRecipesLex();
        });
    }

    public void removeRecipe(Recipe Recipe) {
        this.getChildren().remove(Recipe);
        recipeContainer.remove(Recipe);
        filterRecipes();
        sortRecipes();
    }

    public void clearRecipes() {
        ArrayList<Node> filterAndOtherNodes = new ArrayList<>();
        for (Node node : getChildren()) {
            if (node == filterDropdown || node == filterTimeDropdown || node == filterSortDropdown) {
                filterAndOtherNodes.add(node);
            }
        }
        getChildren().clear();
        getChildren().addAll(filterAndOtherNodes);
        recipeContainer.clear();
    }

    public void sortRecipesLex() {
        try {
            if (!sortOptions.getCheckModel().getCheckedItems().isEmpty()) {
                String selectedSort = sortOptions.getCheckModel().getCheckedItems().get(0);
                if (selectedSort.equals("A-Z")) {
                    Collections.sort(recipeContainer,
                            (r1, r2) -> r1.getNameText().compareToIgnoreCase(r2.getNameText()));
                } else if (selectedSort.equals("Z-A")) {
                    Collections.sort(recipeContainer,
                            (r1, r2) -> r2.getNameText().compareToIgnoreCase(r1.getNameText()));
                }
                renderRecipes();
            }
        } catch (Exception e) {
            System.out.println("Error sorting recipes alphabetically: " + e.getMessage());
        }
    }

    public void sortRecipes() {
        try {
            if (!timeOptions.getCheckModel().getCheckedItems().isEmpty()) {
                String selectedTime = timeOptions.getCheckModel().getCheckedItems().get(0);
                if (selectedTime.equals("Newest")) {
                    Collections.sort(recipeContainer, (r1, r2) -> {
                        // Compare creation time for sorting from newest to oldest
                        LocalDateTime time1 = r1.getRecipeIdAsDateTime();
                        LocalDateTime time2 = r2.getRecipeIdAsDateTime();
                        System.out.println("hi");
                        return time2.compareTo(time1);
                    });
                } else if (selectedTime.equals("Oldest")) {
                    Collections.sort(recipeContainer, (r1, r2) -> {
                        // Compare creation time for sorting from oldest to newest
                        LocalDateTime time1 = r1.getRecipeIdAsDateTime();
                        LocalDateTime time2 = r2.getRecipeIdAsDateTime();
                        return time1.compareTo(time2);
                    });
                }
                renderRecipes();
            }
        } catch (Exception e) {
            System.out.println("Error sorting recipes: " + e.getMessage());
        }
    }

    private void renderRecipes() {
        // Clear only recipes, not dropdowns
        ArrayList<Node> dropdowns = new ArrayList<>();

        for (Node node : this.getChildren()) {
            if (node == filterDropdown || node == filterTimeDropdown || node == filterSortDropdown) {
                dropdowns.add(node);
            }
        }
        this.getChildren().clear();
        this.getChildren().addAll(dropdowns); // Add dropdowns back

        for (Recipe recipe : recipeContainer) {
            this.getChildren().add(recipe);
        }

    }

    // loadTasks from Mongo
    public void loadTasks(String id) {
        List<Document> recipeList = server.LoadRecipes.loadRecipes(id);
        for (Document recipeDoc : recipeList) {
            Recipe recipe = new Recipe(appFrame);

            String recipeName = recipeDoc.getString("recipeName");
            String recipeIngredients = recipeDoc.getString("recipeIngredients");
            String recipeInstructions = recipeDoc.getString("recipeInstructions");
            String recipeId = recipeDoc.getString("_id");
            String mealType = recipeDoc.getString("mealType");
            String url = recipeDoc.getString("url");

            recipe.getName().setText(recipeName);
            recipe.getIngredient().setText(recipeIngredients);
            recipe.getInstruction().setText(recipeInstructions);
            recipe.setRecipeId(recipeId);
            recipe.getMealType().setText(mealType);
            recipe.getImageURL().setText(url);
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
            if (mealType.charAt(mealType.length() - 1) == '.') {
                mealType = mealType.substring(0, mealType.length() - 1);
            }
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
        sortRecipes();
    }

}