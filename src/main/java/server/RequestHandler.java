package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.Scanner;

public class RequestHandler implements HttpHandler {
   private final Map<String, String> data;

   public RequestHandler(Map<String, String> var1) {
      this.data = var1;
   }

   public void handle(HttpExchange var1) throws IOException {
      String var2 = "Request Received";
      String var3 = var1.getRequestMethod();

      try {
         if (var3.equals("GET")) {
            var2 = this.handleGet(var1);
         } else if (var3.equals("POST")) {
            var2 = this.handlePost(var1);
         } else if (var3.equals("PUT")) {
            var2 = this.handlePut(var1);
         } else {
            if (!var3.equals("DELETE")) {
               throw new Exception("Not Valid Request Method");
            }

            var2 = this.handleDelete(var1);
         }
      } catch (Exception var5) {
         System.out.println("An erroneous request");
         var2 = var5.toString();
         var5.printStackTrace();
      }

      var1.sendResponseHeaders(200, (long)var2.length());
      OutputStream var4 = var1.getResponseBody();
      var4.write(var2.getBytes());
      var4.close();
   }

   private String handleGet(HttpExchange var1) throws IOException {
    String var2 = "Invalid GET request";
    URI var3 = var1.getRequestURI();
    String var4 = var3.getPath();  // Get the path from the URI

    if (var4.startsWith("/recipe/")) {
        String recipeId = var4.substring("/recipe/".length());
        // Handle the recipe URL, fetch details based on recipeId, and return HTML response
        // You need to implement this part based on your data model
        var2 = generateRecipeHTML(recipeId);
    }

    return var2;
    }

    private String generateRecipeHTML(String recipeId) {
        // Fetch recipe details from MongoDB based on recipeId
        String recipeDetails = fetchRecipeDetailsFromMongoDB(recipeId);
    
        String html = "<html><head><title>Recipe Details</title></head><body>";
        html += "<h1>Recipe Details</h1>";
        html += "<p>" + recipeDetails + "</p>";
        html += "</body></html>";
    
        return html;
    }
    
    // Implement a method to fetch recipe details from MongoDB based on recipeId
    private String fetchRecipeDetailsFromMongoDB(String recipeId) {
        return MongoDB.fetchRecipeDetails(recipeId);
    }
    

   private String handlePost(HttpExchange var1) throws IOException {
      InputStream var2 = var1.getRequestBody();
      Scanner var3 = new Scanner(var2);
      String var4 = var3.nextLine();
      String var5 = var4.substring(0, var4.indexOf(","));
      String var6 = var4.substring(var4.indexOf(",") + 1);
      this.data.put(var5, var6);
      String var7 = "Posted entry {" + var5 + ", " + var6 + "}";
      System.out.println(var7);
      var3.close();
      return var7;
   }

   private String handleDelete(HttpExchange var1) throws IOException {
      String var2 = "Invalid GET request";
      URI var3 = var1.getRequestURI();
      String var4 = var3.getRawQuery();
      if (var4 != null) {
         String var5 = var4.substring(var4.indexOf("=") + 1);
         String var6 = (String)this.data.get(var5);
         if (var6 != null) {
            this.data.remove(var5);
            var2 = "Deleted entry {" + var5 + ", " + var6 + "}";
         } else {
            var2 = "No data found for " + var5;
         }
      }

      return var2;
   }

   private String handlePut(HttpExchange var1) throws IOException {
      InputStream var2 = var1.getRequestBody();
      Scanner var3 = new Scanner(var2);
      String var4 = var3.nextLine();
      String var5 = var4.substring(0, var4.indexOf(","));
      String var6 = var4.substring(var4.indexOf(",") + 1);
      String var7 = (String)this.data.get(var5);
      String var8 = "";
      if (var7 != null) {
         var8 = "Updated entry {" + var5 + ", " + var6 + "} (previous year: " + var7 + ")";
      } else {
         var8 = "Added entry {" + var5 + ", " + var6 + "}";
      }

      this.data.put(var5, var6);
      System.out.println(var8);
      var3.close();
      return var8;
   }
}
