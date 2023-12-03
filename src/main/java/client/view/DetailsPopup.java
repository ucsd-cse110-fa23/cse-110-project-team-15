package client.view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DetailsPopup extends Stage {

    private Recipe recipe;

    private TextField name;
    private TextField ingredients;
    private TextArea instruction;

    private Button editButton;
    private Button deleteButton;
    private Button saveButton;
    private Button backButton;
    private Button refreshButton;

    public DetailsPopup() {
        // setTitle(name.getText());
        setWidth(525);
        setHeight(650);
        // Create controls for the popup window
        name = new TextField();
        this.name.setPrefSize(200, 10); // set size of text field
        this.name.setStyle(
                " -fx-background-color: #659966;  -fx-font-weight: bold; -fx-font-size: 17; -fx-font-family: 'Times New Roman';");
        this.name.setAlignment(Pos.CENTER);
        this.name.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the text field
        name.setEditable(false);

        ingredients = new TextField();
        ingredients.setPrefSize(50, 10); // set size of text field
        ingredients.setStyle(" -fx-font-weight: bold; -fx-font-size: 14; -fx-font-family: 'Times New Roman';");
        ingredients.setEditable(false);

        instruction = new TextArea();
        this.instruction.setPrefSize(200, 100); // set size of text field
        instruction.setStyle(" -fx-font-weight: bold; -fx-font-size: 14; -fx-font-family: 'Times New Roman';");
        this.instruction.setEditable(false);
        this.instruction.setWrapText(true);

        String defaultButtonStyle = ("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Times New Roman';");

        backButton = new Button("<-");
        backButton.setStyle(
                "-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Arial';");
        editButton = new Button("Edit");
        editButton.setStyle(defaultButtonStyle);
        saveButton = new Button("Save");
        saveButton.setStyle(defaultButtonStyle);
        deleteButton = new Button("Delete");
        deleteButton.setStyle(defaultButtonStyle);
        refreshButton = new Button("Refresh");
        refreshButton.setStyle(defaultButtonStyle);

        // Create a layout for the controls
        VBox layout = new VBox(10); // 10 pixels spacing
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(name, ingredients, instruction, backButton, editButton, deleteButton, saveButton,
                refreshButton);


        editButton.setOnAction(e -> {
            ingredients.setEditable(!ingredients.isEditable());
            instruction.setEditable(!instruction.isEditable());
        });


        backButton.setOnAction(e -> {
            close();
        });

        // Create a scene and set it for the popup window
        Scene scene = new Scene(layout);
        setScene(scene);
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        name.setText(recipe.getName().getText());
        ingredients.setText(recipe.getIngredient().getText());
        instruction.setText(recipe.getInstruction().getText());
    }

    public Button getEditButton() {
        return this.editButton;
    }

    public Button getDeleteButton() {
        return this.deleteButton;
    }

    public Button getSaveButton() {
        return this.saveButton;
    }

    public Button getRefreshButton() {
        return this.refreshButton;
    }

    public TextField getName() {
        return name;
    }
    
    public TextField getIngredients() {
        return ingredients;
    }

    public TextArea getInstruction() {
        return instruction;
    }

    public void setRefreshButtonAction(EventHandler<ActionEvent> eventHandler) {
        refreshButton.setOnAction(eventHandler);
    }

    public void setSaveButtonAction(EventHandler<ActionEvent> eventHandler) {
        saveButton.setOnAction(eventHandler);
    }

    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
        deleteButton.setOnAction(eventHandler);
    }
}