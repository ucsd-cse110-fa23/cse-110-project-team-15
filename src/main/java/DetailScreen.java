import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailScreen extends Scene {

    private Recipe recipe;
    private TextField name;
    private TextField ingredients;
    private TextField instruction;

    public DetailScreen(Recipe recipe) {
        super(new VBox(), 300, 200);

        this.recipe = recipe;

        name = new TextField(recipe.getName().getText());
        name.setEditable(false);
        ingredients = new TextField(recipe.getIngredient().getText());
        ingredients.setEditable(false);
        instruction = new TextField(recipe.getInstruction().getText());
        instruction.setEditable(false);

        Button backButton = new Button("<-");
        Button editButton = new Button("Edit");
        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(name, ingredients, instruction, backButton, editButton, deleteButton, saveButton);

        saveButton.setOnAction(e -> {
            recipe.getName().setText(name.getText());
            recipe.getIngredient().setText(ingredients.getText());
            recipe.getInstruction().setText(instruction.getText());
            recipe.saveRecipe();
            goBackToRecipeScreen();
            // Handle switching back to the Recipe screen
            //Stage stage = (Stage) getScene().getWindow();
            //stage.setScene(recipe.getScene());
        });

        editButton.setOnAction(e -> {
            ingredients.setEditable(!ingredients.isEditable());
            instruction.setEditable(!instruction.isEditable());
        });

        deleteButton.setOnAction(e -> {
            recipe.deleteRecipe();
            recipe.saveRecipe();
            goBackToRecipeScreen();
            // Handle switching back to the Recipe screen
            //Stage stage = (Stage) getScene().getWindow();
            //stage.setScene(recipe.getScene());
        });

        backButton.setOnAction(e -> {
            Stage stage = (Stage) getRoot().getScene().getWindow();
            stage.close();
            // Handle switching back to the Recipe screen
            //Stage stage = (Stage) getScene().getWindow();
            //stage.setScene(recipe.getScene());
        });

        setRoot(layout);
    }

    private void goBackToRecipeScreen() {
        Stage stage = (Stage) getRoot().getScene().getWindow();
        stage.setScene(new Scene(recipe));
    }
}
