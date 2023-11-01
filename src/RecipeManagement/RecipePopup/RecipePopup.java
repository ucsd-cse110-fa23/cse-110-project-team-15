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
    Label optionsLabel;
    Label optionsText;
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

        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startRecordingButton, stopRecordingButton);

        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(optionsLabel, optionsText, buttonBox, recordingStatusLabel);
        
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

            Whisper.transcribeAudio();

            startRecordingButton.setDisable(false);
        });
    }

    public void display() {
        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);
        this.show();
    }
}