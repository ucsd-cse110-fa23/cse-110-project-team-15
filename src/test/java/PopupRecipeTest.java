import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.InterruptedException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PopupRecipeTest  extends ApplicationTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Initialize JavaFX Toolkit
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

    @Test
    public void audioToIngredientTest() {
        Platform.runLater(() -> {
            String ingredients = "1 cup flour, 2 eggs, sugar, vanilla";
            Recipe recipe = new Recipe();
            Whisper whisp = mock(Whisper.class);
            ChatGPT gpt = mock(ChatGPT.class);
            RecipePopup recipePopup = new RecipePopup(recipe, whisp, gpt);
            when(whisp.transcribeAudio()).thenReturn(ingredients);

            recipePopup.audioToIngredient();
            assertEquals("1 cup flour, 2 eggs, sugar, vanilla", recipePopup.getRecipe().getIngredient().getText());
        });
    }

    @Test
    public void generateInstructionTest() throws IOException, InterruptedException, URISyntaxException{
        Platform.runLater(() -> {
            String mealType = "Lunch";
            String ingredients = "1 cup flour, 2 eggs, sugar";
            String response = "Pancakes - flour, eggs - Mix flour and eggs";
            Recipe recipe = new Recipe();
            recipe.getMealType().setText(mealType);
            recipe.getIngredient().setText(ingredients);

            Whisper whisp = mock(Whisper.class);
            ChatGPT gpt = mock(ChatGPT.class);
            RecipePopup recipePopup = new RecipePopup(recipe, whisp, gpt);

            System.out.println(recipePopup.getRecipe().getName().getText());
            System.out.println("hi");
            

            try {
                when(gpt.generate(anyString())).thenReturn(response);
                recipePopup.generateInstruction();
                System.out.println(recipePopup.getRecipe().getName().getText());
                assertEquals("Pancakes ", recipePopup.getRecipe().getName().getText());
                assertEquals(" flour, eggs ", recipePopup.getRecipe().getIngredient().getText());
                assertEquals(" Mix flour and eggs", recipePopup.getRecipe().getInstruction().getText());
                assertFalse(recipePopup.mealTypeSet);
            } catch (Exception e) {
                fail("Exception occurred during instruction generation.");
            }
        });
    }
}
