import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import client.controller.Controller;
import client.model.Model;
import client.view.AppFrame;
import client.view.Recipe;
import client.view.RecipeList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class FilterTest extends ApplicationTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        App main = new App();
        main.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void filterByOneMealType() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        RecipeList list = new RecipeList(mockAppFrame);
        Mockito.when(mockAppFrame.getRecipeList()).thenReturn(list);

        Recipe recipe1 = new Recipe(mockAppFrame);
        recipe1.getName().setText("Recipe 1");
        recipe1.getMealType().setText("breakfast");
        recipe1.getIngredient().setText("ingredient");
        recipe1.getInstruction().setText("instruction");
        recipe1.addRecipe();

        Recipe recipe2 = new Recipe(mockAppFrame);
        recipe2.getName().setText("Recipe 2");
        recipe2.getMealType().setText("lunch");
        recipe2.getIngredient().setText("ingredient");
        recipe2.getInstruction().setText("instruction");
        recipe2.addRecipe();

        Recipe recipe3 = new Recipe(mockAppFrame);
        recipe3.getName().setText("Recipe 3");
        recipe3.getMealType().setText("breakfast");
        recipe3.getIngredient().setText("ingredient");
        recipe3.getInstruction().setText("instruction");
        recipe3.addRecipe();

        Recipe recipe4 = new Recipe(mockAppFrame);
        recipe4.getName().setText("Recipe 4");
        recipe4.getMealType().setText("dinner");
        recipe4.getIngredient().setText("ingredient");
        recipe4.getInstruction().setText("instruction");
        recipe4.addRecipe();

        list.mealOptions.getCheckModel().check("Breakfast");
        assertEquals(1, list.mealOptions.getCheckModel().getCheckedItems().size());

        list.filterRecipes();
        for (Node node : list.getChildren()) {
            if (node instanceof Recipe) {
                Recipe recipe = (Recipe) node;
                assertEquals("breakfast", recipe.getMealType().getText().toLowerCase());
            }
        }
    }

    @Test
    public void filterByMultiMealType() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        RecipeList list = new RecipeList(mockAppFrame);
        Mockito.when(mockAppFrame.getRecipeList()).thenReturn(list);

        Recipe recipe1 = new Recipe(mockAppFrame);
        recipe1.getName().setText("Recipe 1");
        recipe1.getMealType().setText("breakfast");
        recipe1.getIngredient().setText("ingredient");
        recipe1.getInstruction().setText("instruction");
        recipe1.addRecipe();

        Recipe recipe2 = new Recipe(mockAppFrame);
        recipe2.getName().setText("Recipe 2");
        recipe2.getMealType().setText("lunch");
        recipe2.getIngredient().setText("ingredient");
        recipe2.getInstruction().setText("instruction");
        recipe2.addRecipe();

        Recipe recipe3 = new Recipe(mockAppFrame);
        recipe3.getName().setText("Recipe 3");
        recipe3.getMealType().setText("breakfast");
        recipe3.getIngredient().setText("ingredient");
        recipe3.getInstruction().setText("instruction");
        recipe3.addRecipe();

        Recipe recipe4 = new Recipe(mockAppFrame);
        recipe4.getName().setText("Recipe 4");
        recipe4.getMealType().setText("dinner");
        recipe4.getIngredient().setText("ingredient");
        recipe4.getInstruction().setText("instruction");
        recipe4.addRecipe();

        list.mealOptions.getCheckModel().check("Breakfast");
        list.mealOptions.getCheckModel().check("Lunch");
        assertEquals(2, list.mealOptions.getCheckModel().getCheckedItems().size());

        list.filterRecipes();
         for (Node node : list.getChildren()) {
            if (node instanceof Recipe) {
                Recipe recipe = (Recipe) node;
                assertTrue(recipe.getMealType().getText().toLowerCase().equals("breakfast") ||
                recipe.getMealType().getText().toLowerCase().equals("lunch"), "Meal type should be either 'breakfast' or 'lunch'");
            }
        }
    }

    @Test
    public void deselectFilter() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        RecipeList list = new RecipeList(mockAppFrame);
        Mockito.when(mockAppFrame.getRecipeList()).thenReturn(list);

        Recipe recipe1 = new Recipe(mockAppFrame);
        recipe1.getName().setText("Recipe 1");
        recipe1.getMealType().setText("breakfast");
        recipe1.getIngredient().setText("ingredient");
        recipe1.getInstruction().setText("instruction");
        recipe1.addRecipe();

        Recipe recipe2 = new Recipe(mockAppFrame);
        recipe2.getName().setText("Recipe 2");
        recipe2.getMealType().setText("lunch");
        recipe2.getIngredient().setText("ingredient");
        recipe2.getInstruction().setText("instruction");
        recipe2.addRecipe();

        Recipe recipe3 = new Recipe(mockAppFrame);
        recipe3.getName().setText("Recipe 3");
        recipe3.getMealType().setText("breakfast");
        recipe3.getIngredient().setText("ingredient");
        recipe3.getInstruction().setText("instruction");
        recipe3.addRecipe();

        Recipe recipe4 = new Recipe(mockAppFrame);
        recipe4.getName().setText("Recipe 4");
        recipe4.getMealType().setText("dinner");
        recipe4.getIngredient().setText("ingredient");
        recipe4.getInstruction().setText("instruction");
        recipe4.addRecipe();

        list.mealOptions.getCheckModel().check("Breakfast");
        list.mealOptions.getCheckModel().clearCheck("Breakfast");
        assertEquals(0, list.mealOptions.getCheckModel().getCheckedItems().size());
    }
}
