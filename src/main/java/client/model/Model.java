/* 
 * Sources:
 * https://chat.openai.com/
 * date: 11/13/2023
 * version number: GPT-4
 * prompt1: how do i make a http request in the front-end that posts the prompt string to the localhost and get the response from chatgpt api
 * prompt2: how to send a request that contains the audio file we just recorded
 * 
 * Lab 5
 * date: 11/13/2023
 * Copy code from our lab 5 github
*/

package client.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Files;
import java.nio.file.Path;
import org.bson.types.ObjectId;

import client.view.Recipe;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import client.view.Recipe;
import server.Login;

import java.net.URI;

public class Model {
    // Doesnt work
    public String recipeRequest(String method, String language, String year, String query) {
        try {
            String urlString = "http://localhost:8100/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(language + "," + year);
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String sendAccount(String username, String password) {
        String url = "http://localhost:8100/api/accounts";

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject json = new JSONObject();
            json.put("_id", new ObjectId());
            json.put("username", username);
            json.put("password", password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            System.out.println("username on server: " + json.toString());

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String sendRecipe(Recipe recipe) {
        String url = "http://localhost:8100/api/recipes";

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject json = new JSONObject();
            json.put("id", server.Login.getID());
            json.put("name", recipe.getName().getText());
            json.put("mealType", recipe.getMealType().getText());
            json.put("ingredients", recipe.getIngredient().getText());
            json.put("instructions", recipe.getInstruction().getText());

            System.out.println(json);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String deleteRecipe(Recipe recipe) {
        String url = "http://localhost:8100/api/delete";

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject json = new JSONObject();
            json.put("id", server.Login.getID());
            json.put("name", recipe.getName().getText());
            json.put("mealType", recipe.getMealType().getText());
            json.put("ingredients", recipe.getIngredient().getText());
            json.put("instructions", recipe.getInstruction().getText());

            System.out.println(json);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    

    // Doenst work all the time because of byte fixed content-lenth
    public String requestInstruction(String prompt) {
        String url = "http://localhost:8100/instruction";

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("prompt", prompt);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .method("PUT", HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public String requestTranscript() {
        String url = "http://localhost:8100/transcribe";
        String filePath = "bin/audio/recording.wav";
        try {
            Path path = Path.of(filePath);
            byte[] fileContent = Files.readAllBytes(path);
            String response = sendAudio(url, filePath);
            System.out.println("Response: " + response);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    public static String sendAudio(String url, String filePath) throws IOException, InterruptedException {
        File audioFile = new File(filePath);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "audio/wav")
                .POST(BodyPublishers.ofFile(audioFile.toPath()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response as JSON and get the transcript field
        return response.body();
    }

    public static String generateImage(String prompt) throws IOException, InterruptedException {
        String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
        String API_KEY = "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";
        String MODEL = "dall-e-2";
        int n = 1;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");

        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString());

        // Process the response
        String responseBody = response.body();

        JSONObject responseJson = new JSONObject(responseBody);

        // TODO: Process the response
        String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");
        return generatedImageURL;

    }



}