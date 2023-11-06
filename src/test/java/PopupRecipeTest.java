import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PopupRecipeTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Initialize JavaFX Toolkit
    }

    @Test
    public void audioToIngredientTest() {
        Platform.runLater(() -> {
            String ingredients = "1 cup flour, 2 eggs, sugar";
            Recipe recipe = new Recipe();
            Whisper whisp = mock(Whisper.class);
            RecipePopup recipePopup = new RecipePopup(recipe, whisp);
            when(whisp.transcribeAudio()).thenReturn(ingredients);

            recipePopup.audioToIngredient();
            assertEquals("1 cup flour, 2 eggs, sugar", recipePopup.getRecipe().getIngredient().getText());
        });
    }

    @Test
    public void generateInstructionTest() {
        Platform.runLater(() -> {
            String mealType = "Lunch";
            String ingredients = "1 cup flour, 2 eggs, sugar";
            String response = "Pancakes - flour, eggs - Mix flour and eggs";

            Recipe recipe = new Recipe();
            recipe.getMealType().setText(mealType);
            recipe.getIngredient().setText(ingredients);

            Whisper whisp = mock(Whisper.class);
            RecipePopup recipePopup = new RecipePopup(recipe, whisp);
            when(whisp.transcribeAudio()).thenReturn(mealType);
            when(whisp.transcribeAudio()).thenReturn(ingredients);
            when(whisp.transcribeAudio()).thenReturn(response);

            try {
                recipePopup.generateInstruction();

                assertEquals("Pancakes", recipePopup.getRecipe().getName().getText());
                assertEquals("1 cup flour, 2 eggs, sugar", recipePopup.getRecipe().getIngredient().getText());
                assertEquals("Mix flour and eggs", recipePopup.getRecipe().getInstruction().getText());
                assertFalse(recipePopup.mealTypeSet);
            } catch (Exception e) {
                fail("Exception occurred during instruction generation.");
            }
        });
    }
}
