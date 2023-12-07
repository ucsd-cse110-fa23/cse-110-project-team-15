import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import server.MongoDB;

import client.view.DetailsPopup;
import javafx.application.Platform;
import server.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.*;
import java.net.URI;
import java.text.Normalizer;
import java.net.URISyntaxException;

public class MS2Test extends ApplicationTest {

    @Test
    public void MS2Test() throws IOException, InterruptedException, URISyntaxException, Exception{
        // AudioRecorderTest t1 = new AudioRecorderTest();
        AutoLoginTest t2 = new AutoLoginTest();
        ChatGPTTest t3 = new ChatGPTTest();
        CreateAccountTest t4 = new CreateAccountTest();
        DispMealTypeTest t5 = new DispMealTypeTest();
        FilterByMealTest t6 = new FilterByMealTest();
        ImagePreviewTest t7 = new ImagePreviewTest();
        LoginTest t8 = new LoginTest();
        MongoTest t9 = new MongoTest();
        MS2Test t10 = new MS2Test();
        RecipeListTest t11 = new RecipeListTest();
        RecipeTest t12 = new RecipeTest();
        // RequestHandlerTest t13 = new RequestHandlerTest();
        ShareURLTest t14 = new ShareURLTest(); 
        SortRecipeTest t15 = new SortRecipeTest();
        WhisperTest t16 = new WhisperTest();
        ServerTest t17 = new ServerTest();

        t2.testAutoLoginE2E();
        t3.testChatGPTE2E();
        t4.testCreateAccountE2E();
        t6.testFilterByMealE2E();
        t7.testImagePreviewE2E();
        t8.testLoginE2E();
        t12.testRecipeTestE2E();
        t14.testShareURLE2E();
        t16.testWhisperE2E();
        t17.testServerE2E();

    }
}
