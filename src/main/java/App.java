import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import client.view.AppFrame;
import client.view.ServerUnavailable;
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
        
                boolean isServerOnline = controller.checkServerStatus();

        if (isServerOnline) {
            primaryStage.setResizable(true);
            primaryStage.setScene(new Scene(root, 600, 600));
            primaryStage.setTitle("Recipe Management App");
            primaryStage.show();
        } else {
            // Server is offline, show the "Server Unavailable" window
            ServerUnavailable serverUnavailable = root.getServerUnavailable();
            serverUnavailable.show();
        }
    }
}