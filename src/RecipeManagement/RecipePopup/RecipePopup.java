package src.RecipeManagement.RecipePopup;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import java.awt.TextArea;
import java.io.*;
import java.net.*;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.json.JSONException;
import org.json.JSONObject;

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
        // recordingStatusLabel.setTextFill(Color.RED);
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
                    Whisper.transcribeAudio();
                }
            }
        }
    }

    public class Whisper {

        private static final String API_ENDPOINT = "https://api.openai.com/v1/audio/transcriptions";
        private static final String TOKEN = "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";
        private static final String MODEL = "whisper-1";
        private static final String FILE_PATH = "src/RecipeManagement/RecipePopup/recording.wav";

        // Helper method to write a parameter to the output stream in multipart form
        // data format
        private static void writeParameterToOutputStream(
                OutputStream outputStream,
                String parameterName,
                String parameterValue,
                String boundary) throws IOException {
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(
                    ("Content-Disposition: form-data; name=\"" + parameterName + "\"\r\n\r\n").getBytes());
            outputStream.write((parameterValue + "\r\n").getBytes());
        }

        // Helper method to write a file to the output stream in multipart form data
        // format
        private static void writeFileToOutputStream(
                OutputStream outputStream,
                File file,
                String boundary) throws IOException {
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(
                    ("Content-Disposition: form-data; name=\"file\"; filename=\"" +
                            file.getName() +
                            "\"\r\n").getBytes());
            outputStream.write(("Content-Type: audio/mpeg\r\n\r\n").getBytes());

            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
        }

        // Helper method to handle a successful response
        private static void handleSuccessResponse(HttpURLConnection connection)
                throws IOException, JSONException {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject responseJson = new JSONObject(response.toString());

            String generatedText = responseJson.getString("text");

            // Print the transcription result
            System.out.println("Transcription Result: " + generatedText);
        }

        private static void handleErrorResponse(HttpURLConnection connection)
                throws IOException, JSONException {
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream()));
            String errorLine;
            StringBuilder errorResponse = new StringBuilder();
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            String errorResult = errorResponse.toString();
            System.out.println("Error Result: " + errorResult);
        }

        public static void main(String[] args) throws IOException, URISyntaxException {
            RecipePopup recipePopup = new RecipePopup();
            recipePopup.show();
        }

        private static void transcribeAudio() {
            try {
                File file = new File(FILE_PATH);
                URL url = new URI(API_ENDPOINT).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String boundary = "Boundary-" + System.currentTimeMillis();
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setRequestProperty("Authorization", "Bearer " + TOKEN);

                OutputStream outputStream = connection.getOutputStream();

                writeParameterToOutputStream(outputStream, "model", MODEL, boundary);
                writeFileToOutputStream(outputStream, file, boundary);

                outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    handleSuccessResponse(connection);
                } else {
                    handleErrorResponse(connection);
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}