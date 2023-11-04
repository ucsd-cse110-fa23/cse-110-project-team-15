import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

// import org.junit.Test;
// import static org.junit.Assert;

public class Test1 extends Main {
    private static Application app;

    @BeforeAll
    public static void initializeJavaFX() throws Exception {
        if (Platform.isFxApplicationThread()) {
            app = new Main();
        } else {
            Platform.startup(() -> {
                app = new Main();
            });
        }
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

}
