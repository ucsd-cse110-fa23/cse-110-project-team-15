
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javafx.geometry.Pos;

public class RecipePopup extends Stage {

    private AudioRecorder audioRecorder;
    private Label recordingStatusLabel;
    private static Label errorLabel;
    private static Label optionsLabel;
    private static Label optionsText;
    public static boolean mealTypeSet = false;
    private Recipe recipe;
    
    private Button startRecordingButton;
    private Button stopRecordingButton;
    private HBox buttonBox;
    private VBox layout;


    public RecipePopup(Recipe recipe) {


        setTitle("Specify Meal Type");
        setWidth(300);
        setHeight(200);

        this.recipe = recipe;

        optionsLabel = new Label(mealTypeSet ? "Say your ingredients" : "Say one of the following options:");
        optionsLabel.setStyle("-fx-alignment: center; -fx-font-weight: bold;");

        optionsText = new Label(mealTypeSet ? "" : "Breakfast, Lunch, or Dinner");
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
            // TODO: is this correct way to do this? (multithreading maybe if needed?)
            if (mealTypeSet) {
                audioToIngredient();
                try {
                    generateInstruction();
                } catch (IOException | InterruptedException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
                this.close();
            } else {
                audioToMealType();
            }
            startRecordingButton.setDisable(false);
        });
    }



    public void audioToMealType() {
        String generatedText = Whisper.transcribeAudio();
        if (generatedText.toLowerCase().contains("breakfast") 
                || generatedText.toLowerCase().contains("lunch")
                || generatedText.toLowerCase().contains("dinner")) {
            System.out.println("Transcription Result: " + generatedText);
            errorLabel.setVisible(false);
            optionsLabel.setText("List Ingredients");
            optionsLabel.setVisible(true);
            optionsText.setVisible(false);
            recipe.getMealType().setText(generatedText);
            mealTypeSet = true;
        } else {
            System.out.println("Transcription does not contain Breakfast, Lunch, or Dinner.");
            // Show an error message in the popup screen
            errorLabel.setVisible(true);
        }
    }

    public void audioToIngredient() {
        String generatedText = Whisper.transcribeAudio();
        System.out.println("Ingredients: " + generatedText);
        recipe.getIngredient().setText(generatedText);
    }


    public void generateInstruction() throws IOException, InterruptedException, URISyntaxException {
        // TODO: figure out how to parse ChatGPT response for name/ingredients/instructions
        ChatGPT gpt = new ChatGPT();
        String prompt = "List the instructions to making a " + recipe.getMealType().getText() + " with these ingredients " + recipe.getIngredient().getText() +". Respond in this format \"name of recipe - ingredients - instructions\"";
        String instruction = gpt.generate(prompt);
        String[] instructions = instruction.split("-");
        System.out.println("Recipe Name: " + instructions[0]);
        System.out.println("Meal Type: " + recipe.getMealType().getText());
        System.out.println("Ingredients: " + instructions[1]);
        System.out.println("Instructions: " + instructions[2]);
        recipe.getName().setText(instructions[0]);
        recipe.getIngredient().setText(instructions[1]);
        recipe.getInstruction().setText(instructions[2]);
        mealTypeSet = false;
    }

    public void display() {
        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);
        this.show();
    }
}