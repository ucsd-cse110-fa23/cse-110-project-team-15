// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.testfx.api.FxToolkit;
// import org.testfx.framework.junit5.ApplicationTest;
// import javafx.application.Platform;
// import javafx.embed.swing.JFXPanel;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextField;
// import javafx.stage.Stage;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.BeforeAll;

// public class DetailsPopupTest extends ApplicationTest {
// private Recipe recipe;
// private DetailsPopup detailsPopup;

// @BeforeAll
// public static void initJFX() {
// new JFXPanel(); // Initialize JavaFX Toolkit
// }

// @Override
// public void start(Stage primaryStage) throws Exception {
// // This method is called automatically before the test begins.
// Main main = new Main();
// main.start(primaryStage);
// }

// @Override
// public void stop() throws Exception {
// FxToolkit.hideStage();
// }

// @BeforeEach
// public void setUp() {
// recipe = new Recipe();
// recipe.getName().setText("Chicken Curry");
// recipe.getIngredient().setText("Chicken, Curry Sauce, Rice");
// recipe.getInstruction().setText("Cook chicken and add curry sauce. Serve with
// rice");
// detailsPopup = new DetailsPopup(recipe);
// detailsPopup.show();
// }
// }
