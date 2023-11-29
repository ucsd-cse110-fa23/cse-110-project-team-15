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
import client.view.RecipePopup;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
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

        Platform.runLater(() -> {

            AppFrame mockAppFrame = new AppFrame();
            Model model = new Model();
            Controller controller = new Controller(mockAppFrame, model);

            Recipe recipe = new Recipe(mockAppFrame);
            recipe.getMealType().setText("Breakfast");
            recipe.name.setText("Chicken Rice");
            recipe.ingredient.setText("rice");
            recipe.instruction.setText("1.instruction1");

            
            DetailsPopup detailsPopup = new DetailsPopup();
            controller.detailsPopup = detailsPopup;

            detailsPopup.setRecipe(recipe);
            detailsPopup.getRefreshButton().fire();
            try {
                String[] instructions = controller.generateInstruction(recipe.getMealType().getText(), recipe.getIngredient().getText());
                detailsPopup.name.setText(instructions[0]);
                detailsPopup.ingredients.setText(instructions[1]);
                detailsPopup.instruction.setText(instructions[2]);
            } catch (IOException | InterruptedException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            assertTrue(detailsPopup.ingredients.getText().toLowerCase().contains("rice"), "");
            assertNotEquals("Chicken Rice", detailsPopup.name.getText());
            assertNotEquals("1.instruction1", detailsPopup.instruction.getText());
        });
    }

}

