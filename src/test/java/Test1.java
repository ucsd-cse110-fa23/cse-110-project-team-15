import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.embed.swing.JFXPanel;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.control.Label;


import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

// import org.junit.Test;
// import static org.junit.Assert;

public class Test1 extends ApplicationTest {

    @BeforeAll
    public static void initJFX() {
        new JFXPanel(); // Initialize JavaFX Toolkit
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // This method is called automatically before the test begins.
        Main main = new Main();
        main.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void test1() throws Exception{
        assertEquals("hi", "hi");
    }

    @Test
    public void recipeCreate() throws Exception{

        Recipe rec = new Recipe();
        String name = "Chicken salad";
        String mealType = "Lunch";
        String ingredient = "Hello";
        String instruction = "hi";

        rec.getName().setText(name);
        rec.getMealType().setText(mealType);
        rec.getIngredient().setText(ingredient);
        rec.getInstruction().setText(instruction);

        assertEquals(rec.getName().getText(), name);
        assertEquals(rec.getMealType().getText(), mealType);
        assertEquals(rec.getIngredient().getText(), ingredient);
        assertEquals(rec.getInstruction().getText(), instruction);
    }

    @Test
    public void mealValidTest() throws Exception{
        Platform.runLater(() -> {
            String mealType = "Lunch";
            String name = "Salad";
            Recipe recipe = new Recipe();
            recipe.getName().setText(name);
            Whisper whisp = mock(Whisper.class);
            ChatGPT gpt = mock(ChatGPT.class);
            RecipePopup recipePopup = new RecipePopup(recipe, whisp, gpt);
            when(whisp.transcribeAudio()).thenReturn(mealType); // Adjust as needed for your test cases

            recipePopup.audioToMealType();

            // Verify that the appropriate actions have been taken
            assertEquals("Salad", recipePopup.getRecipe().getName().getText());
            assertEquals("Lunch", recipePopup.getRecipe().getMealType().getText());

        });

    }

    @Test
    public void mealInalidTest() throws Exception{
        Platform.runLater(() -> {
            String mealType = "I want a snack";
            String name = "Salad";
            Recipe recipe = new Recipe();
            recipe.getName().setText(name);
            Whisper whisp = mock(Whisper.class);
            ChatGPT gpt = mock(ChatGPT.class);
            RecipePopup recipePopup = new RecipePopup(recipe, whisp, gpt);
            when(whisp.transcribeAudio()).thenReturn(mealType); // Adjust as needed for your test cases

            recipePopup.audioToMealType();

            // Verify that the appropriate actions have been taken
            assertEquals("Salad", recipePopup.getRecipe().getName().getText());
            assertFalse("lunch" == recipePopup.getRecipe().getMealType().getText().toLowerCase());
            assertFalse("dinner" == recipePopup.getRecipe().getMealType().getText().toLowerCase());
            assertFalse("breakfast" == recipePopup.getRecipe().getMealType().getText().toLowerCase());

        });

    }

    @Test
    public void removeRecipeTest() throws Exception {
        Recipe rec = new Recipe();
        String name = "Chicken salad";
        String mealType = "Lunch";
        String ingredient = "Hello";
        String instruction = "hi";

        rec.getName().setText(name);
        rec.getMealType().setText(mealType);
        rec.getIngredient().setText(ingredient);
        rec.getInstruction().setText(instruction);

        RecipeList list = new RecipeList();
        list.getChildren().add(rec);
        list.removeRecipe(rec);

        assertEquals(list.getChildren().size(), 0);
    }

    

}
