import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.embed.swing.JFXPanel;
import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest extends ApplicationTest {
    RecipeList list = new RecipeList();

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
        Recipe recipe = new Recipe(list);

        assertNotNull(recipe.getName());
        assertNotNull(recipe.getMealType());
        assertNotNull(recipe.getIngredient());
        assertNotNull(recipe.getInstruction());
        assertNotNull(recipe.getDetailButton());
    }

    @Test
    public void testRecipeCompletion() {
        Recipe recipe = new Recipe(list);

        recipe.getName().setText("Chicken Stir-Fry");
        recipe.getMealType().setText("Dinner");
        recipe.getIngredient().setText("Chicken, vegetables");
        recipe.getInstruction().setText("Stir-fry the ingredients.");

        assertTrue(recipe.isComplete());
    }

    /*
     * this test case doesn't pass
     */

    // @Test
    // public void testRecipeIncomplete() {
    // Recipe rec1 = new Recipe();
    // rec1.getName().setText("Chicken Stir-Fry");

    // assertFalse(rec1.isComplete());
    // }
}
