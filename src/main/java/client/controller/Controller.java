package client.controller;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URISyntaxException;

import client.model.Model;
import client.view.AppFrame;
import client.view.RecipePopup;
import client.view.AccountPopup;

public class Controller {
    private AppFrame appFrame;
    private Model model;
    private AudioRecorder audioRecorder;
    private RecipePopup recipePopup;
    private AccountPopup accountPopup;

    public Controller(AppFrame appFrame, Model model) {
        this.appFrame = appFrame;
        this.model = model;
        this.audioRecorder = new AudioRecorder();

        this.appFrame.getRecipes();
        this.appFrame.getRecipePopup();
        this.appFrame.getDetailsPopup();
        
        this.recipePopup = appFrame.getRecipePopup();
        this.accountPopup = appFrame.getAccountPopup();

        this.recipePopup.setStartRecordingButtonAction(this::handleStartRecordingButton);
        this.recipePopup.setStopRecordingButtonAction(this::handleStopRecordingButton);
        this.accountPopup.setCreateAccountButtonAction(this::handleCreateAccountButton);

    }

    private void handleCreateAccountButton(ActionEvent event) {
        model.sendAccount(accountPopup.getUsername().getText(), accountPopup.getPassword().getText());
    }

    private void handleStartRecordingButton(ActionEvent event) {
        audioRecorder.startRecording();
        recipePopup.getStartRecordingButton().setDisable(true);
        recipePopup.getStopRecordingButton().setDisable(false);
        recipePopup.getRecordingStatusLabel().setText("Recording...");
        recipePopup.getRecordingStatusLabel().setVisible(true);
    }

    private void handleStopRecordingButton(ActionEvent event) {
        audioRecorder.stopRecording();
        recipePopup.getStopRecordingButton().setDisable(true);
        recipePopup.getRecordingStatusLabel().setText("");
        recipePopup.getRecordingStatusLabel().setVisible(false);
        if (recipePopup.mealTypeSet) {
            audioToIngredient();
            try {
                generateInstruction();
            } catch (IOException | InterruptedException | URISyntaxException e1) {
                e1.printStackTrace();
            }
            recipePopup.close();
            recipePopup.getRecipe().saveRecipe();
        } else {
            audioToMealType();
        }
        recipePopup.getStartRecordingButton().setDisable(false);
    }

    public void audioToMealType() {
        String generatedText = model.requestTranscript();
        System.out.println(generatedText);
        if (generatedText.toLowerCase().contains("breakfast") 
                || generatedText.toLowerCase().contains("lunch")
                || generatedText.toLowerCase().contains("dinner")) {
            System.out.println("Transcription Result: " + generatedText);
            recipePopup.getErrorLabel().setVisible(false);
            recipePopup.getOptionsLabel().setText("List Ingredients");
            recipePopup.getOptionsLabel().setVisible(true);
            recipePopup.getOptionsText().setVisible(false);
            recipePopup.getRecipe().getMealType().setText(generatedText);
            recipePopup.mealTypeSet = true;
        } else {
            System.out.println("Transcription does not contain Breakfast, Lunch, or Dinner.");
            // Show an error message in the popup screen
            recipePopup.getErrorLabel().setVisible(true);
        }
    }

    public void audioToIngredient() {
        String generatedText = model.requestTranscript();
        System.out.println("Ingredients: " + generatedText);
        recipePopup.getRecipe().getIngredient().setText(generatedText);
    }

    public void generateInstruction() throws IOException, InterruptedException, URISyntaxException {
        // TODO: figure out how to parse ChatGPT response for name/ingredients/instructions
        String prompt = "List the instructions to making a " + recipePopup.getRecipe().getMealType().getText() + " with these ingredients " + recipePopup.getRecipe().getIngredient().getText() +". Respond in this format \"name of recipe - ingredients - instructions\"";
        String instruction = model.requestInstruction(prompt);
        String[] instructions = instruction.split("-");
        System.out.println("Recipe Name: " + instructions[0]);
        System.out.println("Meal Type: " + recipePopup.getRecipe().getMealType().getText());
        System.out.println("Ingredients: " + instructions[1]);
        System.out.println("Instructions: " + instructions[2]);
        recipePopup.getRecipe().getName().setText(instructions[0]);
        recipePopup.getRecipe().getIngredient().setText(instructions[1]);
        recipePopup.getRecipe().getInstruction().setText(instructions[2]);
        recipePopup.mealTypeSet = false;
    }


}