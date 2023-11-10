import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeListTest extends ApplicationTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @BeforeEach
    public void setUp() {
        cleanUpCSVFile();
    }

    // Clean up the CSV file after the test
    @AfterEach
    public void tearDown() {
        cleanUpCSVFile();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main main = new Main();
        main.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    // remove recipe test is in Test1.java

    @Test
    public void testSortRecipes() {
        RecipeList list = new RecipeList();
        Recipe recipe1 = new Recipe(list);
        recipe1.getName().setText("Eggs and Hash");
        Recipe recipe2 = new Recipe(list);
        recipe2.getName().setText("Chicken Stir-Fry");
        Recipe recipe3 = new Recipe(list);
        recipe3.getName().setText("Pancakes");

        list.getChildren().add(recipe1);
        list.getChildren().add(recipe2);
        list.getChildren().add(recipe3);

        list.sortRecipes();

        ArrayList<String> expectedOrder = new ArrayList<>(
                Arrays.asList("Chicken Stir-Fry", "Eggs and Hash", "Pancakes"));

        for (int i = 0; i < list.getChildren().size(); i++) {
            assertTrue(list.getChildren().get(i) instanceof Recipe);
            Recipe recipe = (Recipe) list.getChildren().get(i);
            assertEquals(expectedOrder.get(i), recipe.getName().getText());
        }
    }

    @Test
    public void testSaveRecipes() {
        RecipeList list = new RecipeList();

        Recipe recipe1 = new Recipe(list);
        recipe1.getName().setText("Recipe 1");
        list.getChildren().add(recipe1);

        Recipe recipe2 = new Recipe(list);
        recipe2.getName().setText("Recipe 2");
        list.getChildren().add(recipe2);

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
        }
    }

    // Helper method to clean up the CSV file
    private void cleanUpCSVFile() {
        File file = new File("recipes.csv");
        if (file.exists()) {
            file.delete();
        }
    }
}
