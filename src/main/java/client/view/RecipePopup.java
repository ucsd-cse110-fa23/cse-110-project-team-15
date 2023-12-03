package client.view;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

public class RecipePopup extends Stage {

    private Label recordingStatusLabel;
    private static Label errorLabel;
    private static Label optionsLabel;
    private static Label optionsText;
    public boolean mealTypeSet;
    private Recipe recipe;

    private Button startRecordingButton;
    private Button stopRecordingButton;
    private HBox buttonBox;
    private VBox layout;

    public RecipePopup() {
        mealTypeSet = false;

        setTitle("Specify Meal Type");
        setWidth(300);
        setHeight(200);

        optionsLabel = new Label(mealTypeSet ? "Say ingredients" : "Say one of the following options:");
        optionsLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-family: 'Lucida Bright';");

        optionsText = new Label(mealTypeSet ? "" : "Breakfast, Lunch, or Dinner");
        optionsText.setStyle("-fx-alignment: center; -fx-font-weight: bold; -fx-font-family: 'Lucida Bright';");
        startRecordingButton = new Button("Start Recording");
        startRecordingButton.setStyle("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");
        stopRecordingButton = new Button("Stop Recording");
        stopRecordingButton.setStyle("-fx-background-color: #bdd9bd;  -fx-font-weight: bold; -fx-font-size: 13; -fx-font-family: 'Lucida Bright';");
        stopRecordingButton.setDisable(true);

        recordingStatusLabel = new Label();
        // recordingStatusLabel.setTextFill(Color.RED);
        recordingStatusLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        recordingStatusLabel.setVisible(false);

        errorLabel = new Label("Try again");
        errorLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        errorLabel.setVisible(false);

        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startRecordingButton, stopRecordingButton);

        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #93c994;");
        layout.getChildren().addAll(optionsLabel, optionsText, buttonBox, recordingStatusLabel, errorLabel);
    }

    public void setStartRecordingButtonAction(EventHandler<ActionEvent> eventHandler) {
        startRecordingButton.setOnAction(eventHandler);
    }

    public void setStopRecordingButtonAction(EventHandler<ActionEvent> eventHandler) {
        stopRecordingButton.setOnAction(eventHandler);
    }

    public void display() {
        if (this.getScene() == null) {
            Scene scene = new Scene(layout, 400, 500);
            setScene(scene);
        } else {
            
        }
        this.show();
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Button getStartRecordingButton() {
        return this.startRecordingButton;
    }

    public Button getStopRecordingButton() {
        return this.stopRecordingButton;
    }

    public Label getErrorLabel() {
        return this.errorLabel;
    }

    public Label getRecordingStatusLabel() {
        return this.recordingStatusLabel;
    }

    public Label getOptionsLabel() {
        return this.optionsLabel;
    }

    public Label getOptionsText() {
        return this.optionsText;
    }
}