import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import client.view.AppFrame;
import client.model.Model;
import client.controller.Controller;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AppFrame root = new AppFrame();

        Model model = new Model();
        Controller controller = new Controller(root, model);
        
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setTitle("Recipe Management App");
        primaryStage.show();
    }
}