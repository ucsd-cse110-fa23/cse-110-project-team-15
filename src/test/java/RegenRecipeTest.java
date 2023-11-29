import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import client.controller.Controller;
import client.model.Model;
import client.view.AppFrame;
import client.view.DetailsPopup;
import client.view.Recipe;
import client.view.RecipeList;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
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
import static org.mockito.Mockito.when;

public class RegenRecipeTest extends ApplicationTest {

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
    public void testSaveRecipes() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        Recipe recipe = new Recipe(mockAppFrame);
        recipe.name.setText("Scrambled Eggs");
        recipe.ingredient.setText("eggs");
        recipe.instruction.setText("1.Crack, season, and whisk in a mixing bowl..");

        Platform.runLater(() -> {
            DetailsPopup detailsPopup = new DetailsPopup();
            detailsPopup.setRecipe(recipe);
            detailsPopup.refreshButton.fire();

            assertEquals("eggs", detailsPopup.getRecipe().ingredient.getText());
            assertNotEquals("Scrambled Eggs", detailsPopup.getRecipe().name.getText());
            assertNotEquals("1.Crack, season, and whisk in a mixing bowl..", recipe.instruction.getText());
        });
       /*Mockito.when(mockAppFrame.getRecipeList()).thenReturn(list);

        Recipe recipe1 = new Recipe(mockAppFrame);
        recipe1.getName().setText("Recipe 1");
        recipe1.getMealType().setText("breakfast");
        recipe1.getIngredient().setText("ingredient");
        recipe1.getInstruction().setText("instruction");
        recipe1.addRecipe();

        Recipe recipe2 = new Recipe(mockAppFrame);
        recipe2.getName().setText("Recipe 2");
        recipe2.getMealType().setText("breakfast");
        recipe2.getIngredient().setText("ingredient");
        recipe2.getInstruction().setText("instruction");
        recipe2.addRecipe();

        try {
            list.saveRecipes();

            File file = new File("recipes.csv");
            assertTrue(file.exists());
            
            List<String> lines = Files.readAllLines(Path.of("recipes.csv"));

            String[] parts1 = lines.get(0).split("-");
            assertEquals("Recipe 1", parts1[0]);

            String[] parts2 = lines.get(1).split("-");
            assertEquals("Recipe 2", parts2[0]);
        } catch (IOException e) {
            fail("Error occurred: " + e.getMessage());
        }*/
    }
}

