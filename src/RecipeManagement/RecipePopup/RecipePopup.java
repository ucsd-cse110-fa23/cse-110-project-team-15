package RecipeManagement.RecipePopup;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class RecipePopup extends Stage {

    private AudioRecorder audioRecorder;
    private Label recordingStatusLabel;
    private static Label errorLabel;
    private static Label optionsLabel;
    private static Label optionsText;
    public static boolean mealTypeSet = false;
    public static String ingredients;
    public static String mealType;
    Button startRecordingButton;
    Button stopRecordingButton;
    HBox buttonBox;
    VBox layout;

    public RecipePopup() {
        setTitle("Specify Meal Type");
        setWidth(300);
        setHeight(200);

        optionsLabel = new Label("Say one of the following options:");
        optionsLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        optionsText = new Label("Breakfast, Lunch, or Dinner");
        optionsText.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        audioRecorder = new AudioRecorder();
        startRecordingButton = new Button("Start Recording");
        stopRecordingButton = new Button("Stop Recording");
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
        layout.getChildren().addAll(optionsLabel, optionsText, buttonBox, recordingStatusLabel, errorLabel);
        
        addListeners();
    }

    public void addListeners() {
        startRecordingButton.setOnAction(e -> {
            audioRecorder.startRecording();
            startRecordingButton.setDisable(true);
            stopRecordingButton.setDisable(false);
            recordingStatusLabel.setText("Recording...");
            recordingStatusLabel.setVisible(true);
        });

        stopRecordingButton.setOnAction(e -> {
            audioRecorder.stopRecording();
            stopRecordingButton.setDisable(true);
            recordingStatusLabel.setText("");
            recordingStatusLabel.setVisible(false);
            audioToMealType();
            startRecordingButton.setDisable(false);
        });
    }

    public void audioToMealType() {
        String generatedText = Whisper.transcribeAudio();
        if (mealTypeSet) {
            ingredients = generatedText;
            System.out.println("Ingredients:" + generatedText);
        } else {
            if (generatedText.toLowerCase().contains("breakfast") || generatedText.toLowerCase().contains("lunch")
                    || generatedText.toLowerCase().contains("dinner")) {
                System.out.println("Transcription Result: " + generatedText);
                errorLabel.setVisible(false);
                optionsLabel.setText("List Ingredients");
                optionsLabel.setVisible(true);
                optionsText.setVisible(false);
                mealType = generatedText;
                mealTypeSet = true;
            } else {
                System.out.println("Transcription does not contain Breakfast, Lunch, or Dinner.");
                // Show an error message in the popup screen
                errorLabel.setVisible(true);
            }
        }
    }

    public void display() {
        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);
        this.show();
    }
}