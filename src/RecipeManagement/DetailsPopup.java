package RecipeManagement;


import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;

public class DetailsPopup extends Stage {

    private Map<String, String[]> recipe;

    private TextField name;
    private TextField ingredients;
    private TextField instruction;
    
    private Button editButton;
    private Button deleteButton;
    private Button saveButton;
    private Button backButton;
    private HBox buttonBox;
    private VBox layout;


    public DetailsPopup(Map<String, String[]> recipe) {


        setTitle(name.getText());
        setWidth(300);
        setHeight(200);

        this.recipe = recipe;


        buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, editButton, deleteButton, saveButton);

        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(buttonBox);
        
        addListeners();
    }

    public void addListeners() {
    }



    public void display() {
        Scene scene = new Scene(layout, 400, 500);
        setScene(scene);
        this.show();
    }
}