package src.RecipeManagement.RecipePopup;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import java.awt.TextArea;
import java.io.*;
import java.net.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

// import org.json.*;

// public class RecipePopup extends Stage {

//     public RecipePopup() {
//         setTitle("Add New Recipe");
//         setWidth(300);
//         setHeight(150);

//         // Create controls for the popup window
//         Label nameLabel = new Label("Recipe Name:");
//         TextField nameField = new TextField();
//         Button recordButton = new Button("Record");

//         // Create a layout for the controls
//         VBox layout = new VBox(10); // 10 pixels spacing
//         layout.setAlignment(Pos.CENTER);
//         layout.getChildren().addAll(nameLabel, nameField, recordButton);

//         // Add an action for the "Add" button
//         recordButton.setOnAction(e -> {
//             String recipeName = nameField.getText();
//             if (!recipeName.isEmpty()) {
//                 // Add the new recipe to your RecipeList
//                 // RecipeList recipeList = new ;
//                 // Recipe newRecipe = new Recipe();
//                 // newRecipe.getrecipeName().setText(recipeName);
//                 // recipeList.getChildren().add(newRecipe);
//                 close(); // Close the popup window
//             }
//         });

//         // Create a scene and set it for the popup window
//         Scene scene = new Scene(layout);
//         setScene(scene);
//     }
// }

public class RecipePopup extends Stage {

    public RecipePopup() {
        setTitle("Add New Recipe");
        setWidth(300);
        setHeight(150);

        // Create controls for the popup window
        Label nameLabel = new Label("Say one of the following options:\nBreakfast, Lunch, or Dinner");
        Button recordButton = new Button("Record");
        // Button stopButton = new Button("Stop recording");

        // Create a layout for the controls
        VBox layout = new VBox(10); // 10 pixels spacing
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(nameLabel, recordButton);
        AudioRecorder audioRecorder = new AudioRecorder();

        // Add an action for the "Record" button
        recordButton.setOnAction(e -> {
            // Start audio recording
            audioRecorder.startRecording();

            // Provide a way to stop the recording
            // // Provide a way to stop the recording
            Button stopRecordingButton = new Button("Stop Recording");
            layout.getChildren().add(stopRecordingButton);

            // Add an action to stop the recording
            stopRecordingButton.setOnAction(stopEvent -> {
                audioRecorder.stopRecording();

                // Transcribe the recorded audio and update your UI or perform actions as needed
                String transcription = transcribeAudio(audioRecorder.getAudioData());

                // You can use 'transcription' to update your UI or do other actions here

                // Close the popup window
                close();
            });
        });

        // Create a scene and set it for the popup window
        Scene scene = new Scene(layout);
        setScene(scene);
    }

    // This method simulates audio transcription and can be replaced with the actual transcription code
    private String transcribeAudio(byte[] audioData) {
        // Replace this with your actual transcription code
        return "This is a simulated transcription result.";
    }

    public class AudioRecorder {
        private ByteArrayOutputStream audioBuffer = new ByteArrayOutputStream();
        private TargetDataLine targetDataLine;
        private boolean isRecording = false;
    
        public void startRecording() {
            if (!isRecording) {
                isRecording = true;
                AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
                try {
                    targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
                    targetDataLine.open(audioFormat);
                    targetDataLine.start();
    
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int bytesRead;
    
                    System.out.println("Recording audio, press Ctrl+C to stop...");
    
                    while (isRecording) {
                        bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                        audioBuffer.write(buffer, 0, bytesRead);
                    }
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
    
        public byte[] getAudioData() {
            return audioBuffer.toByteArray();
        }
    }
    
}