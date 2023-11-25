package client.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URISyntaxException;

import client.model.Model;
import client.view.AppFrame;
import client.view.DetailsPopup;
import client.view.RecipePopup;

public class Controller {
    private AppFrame appFrame;
    private Model model;
    private AudioRecorder audioRecorder;
    private RecipePopup recipePopup;
    private DetailsPopup detailsPopup;

    public Controller(AppFrame appFrame, Model model) {
        this.appFrame = appFrame;
        this.model = model;
        this.audioRecorder = new AudioRecorder();

        this.appFrame.getRecipes();

        this.detailsPopup = appFrame.getDetailsPopup();
        this.recipePopup = appFrame.getRecipePopup();

        this.recipePopup.setStartRecordingButtonAction(this::handleStartRecordingButton);
        this.recipePopup.setStopRecordingButtonAction(this::handleStopRecordingButton);
        this.detailsPopup.setRefreshButtonAction(this::handleRefreshButton);
    }

    private void handleStartRecordingButton(ActionEvent event) {
        audioRecorder.startRecording();
        recipePopup.getStartRecordingButton().setDisable(true);
        recipePopup.getStopRecordingButton().setDisable(false);
        recipePopup.getRecordingStatusLabel().setText("Recording...");
        recipePopup.getRecordingStatusLabel().setVisible(true);
    }

    private void handleStopRecordingButton(ActionEvent event) {
        String[] instructions;
        audioRecorder.stopRecording();
        recipePopup.getStopRecordingButton().setDisable(true);
        recipePopup.getRecordingStatusLabel().setText("");
        recipePopup.getRecordingStatusLabel().setVisible(false);
        if (recipePopup.mealTypeSet) {
            audioToIngredient();
            try {
                instructions = generateInstruction(recipePopup.getRecipe().getMealType().getText(),
                        recipePopup.getRecipe().getIngredient().getText());
                recipePopup.getRecipe().getName().setText(instructions[0]);
                recipePopup.getRecipe().getIngredient().setText(instructions[1]);
                recipePopup.getRecipe().getInstruction().setText(instructions[2]);
                recipePopup.mealTypeSet = false;

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

    private void handleRefreshButton(ActionEvent event) {
        String[] instructions;
        Button refreshButton = detailsPopup.getRefreshButton();
        refreshButton.setDisable(true);
        try {
            instructions = generateInstruction(detailsPopup.getRecipe().getMealType().getText(),
                    detailsPopup.getRecipe().getIngredient().getText());
            detailsPopup.getRecipe().getName().setText(instructions[0]);
            detailsPopup.getRecipe().getIngredient().setText(instructions[1]);
            detailsPopup.getRecipe().getInstruction().setText(instructions[2]);
            detailsPopup.setRecipe(detailsPopup.getRecipe());
        } catch (IOException | InterruptedException | URISyntaxException e1) {
            e1.printStackTrace();
        }
        detailsPopup.getRecipe().saveRecipe();
        refreshButton.setDisable(false);
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
        recipePopup.getOptionsLabel().setText("Say one of the following options:");
        recipePopup.getOptionsLabel().setVisible(true);
        recipePopup.getOptionsText().setVisible(true);
    }

    public String[] generateInstruction(String mealtype, String ingredients)
            throws IOException, InterruptedException, URISyntaxException {
        // TODO: figure out how to parse ChatGPT response for
        // name/ingredients/instructions
        // System.out.println("")
        String prompt = "List the instructions to making a " + mealtype + " with these ingredients " + ingredients
                + ". Respond in this format \"name of recipe - ingredients - instructions\"";
        String instruction = model.requestInstruction(prompt);
        String[] instructions = instruction.split("-");
        System.out.println("Recipe Name: " + instructions[0]);
        System.out.println("Ingredients: " + instructions[1]);
        System.out.println("Instructions: " + instructions[2]);

        return instructions;
    }
}