import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
            recipe1.setRecipeId(LocalDateTime.now().minusHours(1).toString());
            recipe2.setRecipeId(LocalDateTime.now().toString());
            recipe3.setRecipeId(LocalDateTime.now().minusHours(2).toString());

            recipeList.addRecipe(recipe1);
            recipeList.addRecipe(recipe2);
            recipeList.addRecipe(recipe3);
            recipeList.timeOptions.getCheckModel().check("Newest");
            recipeList.sortRecipes();
            LocalDateTime newestTime = recipeList.getRecipes().get(0).getRecipeIdAsDateTime();

            assertEquals(newestTime, recipe2.getRecipeIdAsDateTime());
        });
    }

    @Test
    public void testSortByOldest() {
        Platform.runLater(() -> {
            Recipe recipe1 = new Recipe(new AppFrame());
            Recipe recipe2 = new Recipe(new AppFrame());
            Recipe recipe3 = new Recipe(new AppFrame());
            recipe1.setRecipeId(LocalDateTime.now().minusHours(1).toString());
            recipe2.setRecipeId(LocalDateTime.now().toString());
            recipe3.setRecipeId(LocalDateTime.now().minusHours(2).toString());

            recipeList.addRecipe(recipe1);
            recipeList.addRecipe(recipe2);
            recipeList.addRecipe(recipe3);
            recipeList.timeOptions.getCheckModel().check("Oldest");
            recipeList.sortRecipes();
            LocalDateTime oldestTime = recipeList.getRecipes().get(0).getRecipeIdAsDateTime();

            assertEquals(oldestTime, recipe3.getRecipeIdAsDateTime());
        });
    }

    @Test
    public void testNoSort() {
        Platform.runLater(() -> {
            Recipe recipe1 = new Recipe(new AppFrame());
            Recipe recipe2 = new Recipe(new AppFrame());
            Recipe recipe3 = new Recipe(new AppFrame());

            recipe1.setRecipeId(LocalDateTime.now().minusHours(1).toString());
            recipe2.setRecipeId(LocalDateTime.now().toString());
            recipe3.setRecipeId(LocalDateTime.now().minusHours(2).toString());

            recipeList.addRecipe(recipe1);
            recipeList.addRecipe(recipe2);
            recipeList.addRecipe(recipe3);

            recipeList.sortRecipes();

            LocalDateTime oldestTime = recipeList.getRecipes().get(0).getRecipeIdAsDateTime();
            LocalDateTime newestTime = recipeList.getRecipes().get(2).getRecipeIdAsDateTime();

            assertEquals(oldestTime, recipe3.getRecipeIdAsDateTime());
            assertEquals(newestTime, recipe2.getRecipeIdAsDateTime());
        });
    }
}
