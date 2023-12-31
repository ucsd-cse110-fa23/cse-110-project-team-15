import client.view.Recipe;
import client.view.AppFrame;
import client.view.DetailsPopup;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DispMealTypeTest extends ApplicationTest {
    private Recipe recipe;

    @Override
    public void start(Stage stage) {
        AppFrame appFrame = new AppFrame();
        recipe = new Recipe(appFrame);
    }

    @Test
    public void testDisplayMealTypeSuccess() {
        Platform.runLater(() -> {
            recipe.getMealType().setText("Breakfast");
            String actualMealType = recipe.getMealType().getText();
            assertEquals("Breakfast", actualMealType);
        });
    }

    @Test
    public void testDisplayNoMealType() {
        Platform.runLater(() -> {
            recipe.getMealType().setText("");

            String actualMealType = recipe.getMealType().getText();
            assertEquals("", actualMealType);
        });
    }

    @Test
    public void testDisplayIncorrectMealType() {
        Platform.runLater(() -> {
            recipe.getMealType().setText("InvalidType");
            String actualMealType = recipe.getMealType().getText();
            assertEquals("InvalidType", actualMealType);
        });
    }

    // this one doesnt work
    @Test
    public void testDispMealTypeE2E() {
        Platform.runLater(() -> {
            testDisplayMealTypeSuccess();
            testDisplayNoMealType();
            testDisplayIncorrectMealType();
        });
    }

        @Test
    public void testUrl() {
                Platform.runLater(() -> {
        DetailsPopup popup = new DetailsPopup();
        String url = popup.buildRecipeUrl("123145134 1231241341");
        assertEquals(url, "http://localhost:8100/recipe/?=123145134_1231241341");
         });
    }
}
