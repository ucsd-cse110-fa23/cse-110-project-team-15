import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import client.view.AppFrame;
import client.view.Recipe;
import javafx.embed.swing.JFXPanel;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest extends ApplicationTest {


    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testRecipeInitialization() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        Recipe recipe = new Recipe(mockAppFrame);

        assertNotNull(recipe.getName());
        assertNotNull(recipe.getMealType());
        assertNotNull(recipe.getIngredient());
        assertNotNull(recipe.getInstruction());
        assertNotNull(recipe.getDetailButton());
        // assertNotNull(recipe.getImageURL());
    }

    @Test
    public void testRecipeCompletion() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        Recipe recipe = new Recipe(mockAppFrame);

        recipe.getName().setText("Chicken Stir-Fry");
        recipe.getMealType().setText("Dinner");
        recipe.getIngredient().setText("Chicken, vegetables");
        recipe.getInstruction().setText("Stir-fry the ingredients.");
        // recipe.getImageURL().setText("https...");

        // assertTrue(recipe.isComplete());
    }

    @Test
    public void testRecipeTestE2E() {
        testRecipeInitialization();
        testRecipeCompletion();
    }
}
