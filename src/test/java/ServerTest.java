import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import client.controller.Controller;
import client.model.Model;
import client.view.AppFrame;
import client.view.Recipe;
import client.view.RecipeList;
import client.view.ServerUnavailable;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import javafx.embed.swing.JFXPanel;
import com.sun.javafx.tk.Toolkit.*;

public class ServerTest extends ApplicationTest{

    @BeforeAll
    public static void initJFX() {
        new JFXPanel();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        App main = new App();
        main.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    public void testServerAvailable() {
        Model mockModel = Mockito.mock(Model.class);
        Mockito.when(mockModel.isServerOnline()).thenReturn(true);

        // Wrapping JavaFX operations in Platform.runLater to execute on the FX Application Thread
        Platform.runLater(() -> {
            assertTrue(mockModel.isServerOnline());
        });
    }

    @Test
    public void testServerUnavailable() {
        Model mockModel = Mockito.mock(Model.class);
        Mockito.when(mockModel.isServerOnline()).thenReturn(false);

        // Wrapping JavaFX operations in Platform.runLater to execute on the FX Application Thread
        Platform.runLater(() -> {
            assertFalse(mockModel.isServerOnline());
        });
    }

    @Test
    public void testServerE2E() {
        testServerAvailable();
        testServerUnavailable();
    }
    
}
