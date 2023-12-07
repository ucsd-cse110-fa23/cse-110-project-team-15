import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import client.view.AppFrame;
import client.view.Recipe;
import client.view.RecipeList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortRecipeTest {
    private RecipeList recipeList;

    @BeforeEach
    public void setUp() {
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            recipeList = new RecipeList(new AppFrame());
        });
    }

    @Test
    public void testSortByNewest() {
        Platform.runLater(() -> {
            Recipe recipe1 = new Recipe(new AppFrame());
            Recipe recipe2 = new Recipe(new AppFrame());
            Recipe recipe3 = new Recipe(new AppFrame());
            recipe1.setRecipeId("2023-12-06 10:00:00.000");
            recipe2.setRecipeId("2023-12-06 11:00:00.000");
            recipe3.setRecipeId("2023-12-06 09:00:00.000");

            recipeList.addRecipe(recipe1);
            recipeList.addRecipe(recipe2);
            recipeList.addRecipe(recipe3);
            recipeList.timeOptions.getCheckModel().check("Newest");
            recipeList.sortRecipes();
            LocalDateTime newestTime = LocalDateTime.parse("2023-12-06 11:00:00.000",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

            assertEquals(newestTime, recipeList.getRecipes().get(0).getRecipeIdAsDateTime());
        });
    }

    @Test
    public void testSortByOldest() {
        Platform.runLater(() -> {
            Recipe recipe1 = new Recipe(new AppFrame());
            Recipe recipe2 = new Recipe(new AppFrame());
            Recipe recipe3 = new Recipe(new AppFrame());
            recipe1.setRecipeId("2023-12-06 10:00:00.000");
            recipe2.setRecipeId("2023-12-06 11:00:00.000");
            recipe3.setRecipeId("2023-12-06 09:00:00.000");

            recipeList.addRecipe(recipe1);
            recipeList.addRecipe(recipe2);
            recipeList.addRecipe(recipe3);
            recipeList.timeOptions.getCheckModel().check("Oldest");
            recipeList.sortRecipes();
            LocalDateTime oldestTime = LocalDateTime.parse("2023-12-06 09:00:00.000",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

            assertEquals(oldestTime, recipeList.getRecipes().get(0).getRecipeIdAsDateTime());
        });
    }

    @Test
    public void testNoSort() {
        Platform.runLater(() -> {
            Recipe recipe1 = new Recipe(new AppFrame());
            Recipe recipe2 = new Recipe(new AppFrame());
            Recipe recipe3 = new Recipe(new AppFrame());
            recipe1.setRecipeId("2023-12-06 10:00:00.000");
            recipe2.setRecipeId("2023-12-06 11:00:00.000");
            recipe3.setRecipeId("2023-12-06 09:00:00.000");

            recipeList.addRecipe(recipe1);
            recipeList.addRecipe(recipe2);
            recipeList.addRecipe(recipe3);
            recipeList.sortRecipes();
            LocalDateTime oldestTime = LocalDateTime.parse("2023-12-06 09:00:00.000",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            LocalDateTime newestTime = LocalDateTime.parse("2023-12-06 11:00:00.000",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

            assertEquals(oldestTime, recipeList.getRecipes().get(0).getRecipeIdAsDateTime());
            assertEquals(newestTime, recipeList.getRecipes().get(2).getRecipeIdAsDateTime());
        });
    }

    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class AllSortRecipeTests {

        @Test
        public void runAllSortTests() {
            SortRecipeTest sortRecipeTest = new SortRecipeTest();

            sortRecipeTest.setUp();
            sortRecipeTest.testSortByNewest();

            sortRecipeTest.setUp();
            sortRecipeTest.testSortByOldest();

            sortRecipeTest.setUp();
            sortRecipeTest.testNoSort();
        }
    }
}
