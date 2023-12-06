package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.Normalizer;
import java.util.Map;
import java.util.Scanner;

public class RequestHandler implements HttpHandler {

   public RequestHandler() {
   }
   public void handle(HttpExchange httpExchange) throws IOException {
      String response = "";
      String method = httpExchange.getRequestMethod();
      
      if (method.equals("GET")) {
          response = handleGet(httpExchange);
      } else {
          response = "Only GET requests are allowed!";
      }
      
      httpExchange.sendResponseHeaders(200, response.length());
      OutputStream outStream = httpExchange.getResponseBody();
      outStream.write(response.getBytes());
      outStream.close();
  }

  private String handleGet (HttpExchange httpExchange) throws UnsupportedEncodingException{
      URI uri = httpExchange.getRequestURI();
      String query = uri.getRawQuery();
      System.out.println(query);
      String name = query != null ? query.substring(query.indexOf("=") + 1) : "Guest";
      
      System.out.println(name);
      name = name.replace("_", " ");

      String[] recipeDetails = MongoDB.fetchRecipeDetails(name);

      String encodedRecipeName = recipeDetails[0];
      String encodedImageUrl = recipeDetails[1];

      String normalizedString = Normalizer.normalize(encodedRecipeName, Normalizer.Form.NFD);
      encodedRecipeName = normalizedString.replaceAll("[^\\p{ASCII}]", "");

      StringBuilder htmlBuilder = new StringBuilder();
      htmlBuilder
          .append("<html><head><title>Recipe Details</title></head><body>")
          .append("<h1>Recipe Details</h1>")
          .append("<p>")
          .append("<img src=\"")
          .append(encodedImageUrl)
          .append("\">")
          .append("<br>")
          .append(encodedRecipeName)
          .append("</p>")
          .append("</body>")
          .append("</html>");

      return htmlBuilder.toString();
  }





}
