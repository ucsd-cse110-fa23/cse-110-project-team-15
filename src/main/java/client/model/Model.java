package client.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import org.bson.types.ObjectId;

import client.view.Recipe;

import org.json.JSONObject;

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
            
            System.out.println("username on server: "+ json.toString());

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
            json.put("name", recipe.getName().getText());
            json.put("mealType", recipe.getMealType().getText());
            json.put("ingredients", recipe.getIngredient().getText());
            json.put("instructions", recipe.getInstruction().getText());

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


    public String requestInstruction(String prompt) {
        String url = "http://localhost:8100/instruction";
        
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)) // Replace with your actual endpoint
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(new JSONObject().put("prompt", prompt).toString()))
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
            String response = sendAudioFile(url, fileContent, path.getFileName().toString());
            System.out.println("Response: " + response);
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error: " + ex.getMessage();
        }
    }

    private static String sendAudioFile(String url, byte[] fileContent, String fileName) throws IOException, InterruptedException {
        String boundary = "----MyBoundary" + System.currentTimeMillis();
        HttpClient client = HttpClient.newHttpClient();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byteStream.write(("--" + boundary + "\r\n").getBytes());
        byteStream.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n").getBytes());
        byteStream.write(("Content-Type: audio/wav\r\n\r\n").getBytes());
        byteStream.write(fileContent);
        byteStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "multipart/form-data; boundary=" + boundary)
            .POST(HttpRequest.BodyPublishers.ofByteArray(byteStream.toByteArray()))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}