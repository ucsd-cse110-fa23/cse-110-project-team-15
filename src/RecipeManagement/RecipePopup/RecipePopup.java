package src.RecipeManagement.RecipePopup;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javax.sound.sampled.*;
import java.io.*;

public class RecipePopup extends Stage {
    private AudioRecorder audioRecorder;
    private Label recordingStatusLabel;

    public RecipePopup() {
        setTitle("Specify Meal Type");
        setWidth(300);
        setHeight(200);

        Label optionsLabel = new Label("Say one of the following options:");
        optionsLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        Label optionsText = new Label("Breakfast, Lunch, or Dinner");
        optionsText.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        audioRecorder = new AudioRecorder();
        Button startRecordingButton = new Button("Start Recording");
        Button stopRecordingButton = new Button("Stop Recording");
        stopRecordingButton.setDisable(true);

        recordingStatusLabel = new Label();
        recordingStatusLabel.setTextFill(Color.RED);
        recordingStatusLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        recordingStatusLabel.setVisible(false);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startRecordingButton, stopRecordingButton);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(optionsLabel, optionsText, buttonBox, recordingStatusLabel);
        startRecordingButton.setOnAction(e -> {
            audioRecorder.startRecording();
            startRecordingButton.setDisable(true);
            stopRecordingButton.setDisable(false);
            recordingStatusLabel.setText("Recording...");
            recordingStatusLabel.setVisible(true);
        });
        stopRecordingButton.setOnAction(e -> {
            audioRecorder.stopRecording();
            startRecordingButton.setDisable(false);
            stopRecordingButton.setDisable(true);
            recordingStatusLabel.setText("");
            recordingStatusLabel.setVisible(false);
        });

        Scene scene = new Scene(layout);
        setScene(scene);
    }

    public class AudioRecorder {
        private TargetDataLine targetDataLine;
        private File audioFile;
        private boolean isRecording;

        public AudioRecorder() {
            isRecording = false;
        }

        public void startRecording() {
            if (!isRecording) {
                isRecording = true;
                AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
                int bufferSize = 4096;
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat, bufferSize);
                try {
                    targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
                    targetDataLine.open(audioFormat);
                    targetDataLine.start();
                    audioFile = new File("src/RecipeManagement/RecipePopup/recording.wav");
                    Thread recordingThread = new Thread(() -> {
                        try (AudioInputStream audioInputStream = new AudioInputStream(targetDataLine)) {
                            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    recordingThread.start();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopRecording() {
            if (isRecording) {
                isRecording = false;
                if (targetDataLine != null) {
                    targetDataLine.stop();
                    targetDataLine.close();
                }
            }
        }
    }
}