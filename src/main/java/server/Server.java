package server;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Server {
  // initialize server port and hostname
  private static final int SERVER_PORT = 8100;
  private static final String SERVER_HOSTNAME = "0.0.0.0";

  // private static String getLocalHostAddress() {
  //   try {
  //     return InetAddress.getLocalHost().getHostAddress();
  //   } catch (IOException e) {
  //     e.printStackTrace();
  //     return "localhost"; // fallback to localhost in case of an exception
  //   }
  // }
  public static void main(String[] args) throws IOException {
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    // create a map to store data
    Map<String, String> data = new HashMap<>();

    // create a server
    HttpServer server = HttpServer.create(
        new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
        0);

    server.createContext("/", new Main());
    server.createContext("/transcribe", new Whisper());
    server.createContext("/instruction", new ChatGPT());
    server.createContext("/api", new MongoDB());
    server.createContext("/accounts", new Account());
    server.createContext("/recipe", new RequestHandler());
    server.setExecutor(threadPoolExecutor);
    server.start();

    System.out.println("Server started on port " + SERVER_PORT);
  }
}