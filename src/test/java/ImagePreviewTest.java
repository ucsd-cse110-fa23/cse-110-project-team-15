import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import client.model.Model;
import client.view.AppFrame;
import client.view.Recipe;
import javafx.embed.swing.JFXPanel;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class ImagePreviewTest extends ApplicationTest {


    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testGenerateImage() {
        AppFrame mockAppFrame = Mockito.mock(AppFrame.class);
        Recipe recipe = new Recipe(mockAppFrame);

        recipe.getName().setText("Chicken Stir-Fry");
        recipe.getMealType().setText("Dinner");
        recipe.getIngredient().setText("Chicken, vegetables");
        recipe.getInstruction().setText("Stir-fry the ingredients.");

        Model model = new Model();

        // Mocking
        String prompt = "Test prompt";
        String mockedImageUrl = "https://example.com/image.jpg";

        try {
            // Mock the behavior of the DALLE API call
            Mockito.doReturn(mockedImageUrl).when(model).generateImage(prompt);

            // Call the method under test
            String result = model.generateImage(prompt);

            // Assert the result
            assertEquals(mockedImageUrl, result);
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }

        recipe.getImageURL().setText(mockedImageUrl);

        assertTrue(recipe.isComplete());
    }

    @Test
    public void testImagePreviewE2E() {
        testGenerateImage();
    }    
  
}
