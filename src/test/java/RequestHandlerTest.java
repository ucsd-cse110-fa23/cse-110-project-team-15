// import com.sun.net.httpserver.HttpExchange;

// import server.RequestHandler;

// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import java.io.ByteArrayInputStream;
// import java.io.ByteArrayOutputStream;
// import java.io.InputStream;
// import java.net.URI;
// import java.util.HashMap;
// import java.util.Map;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// class RequestHandlerTest {

//     @Test
//     void testHandleGet() throws Exception {
//         Map<String, String> data = new HashMap<>();
//         data.put("Java", "1995");

//         RequestHandler requestHandler = new RequestHandler(data);
//         HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
//         URI uri = new URI("/get?language=Java");
//         Mockito.when(httpExchange.getRequestMethod()).thenReturn("GET");
//         Mockito.when(httpExchange.getRequestURI()).thenReturn(uri);

//         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//         Mockito.when(httpExchange.getResponseBody()).thenReturn(outputStream);

//         requestHandler.handle(httpExchange);

//         String response = outputStream.toString();
//         assertEquals("1995", response);
//     }

//     @Test
//     void testHandlePost() throws Exception {
//         Map<String, String> data = new HashMap<>();

//         RequestHandler requestHandler = new RequestHandler(data);
//         HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
//         Mockito.when(httpExchange.getRequestMethod()).thenReturn("POST");

//         InputStream inputStream = new ByteArrayInputStream("Java,1995".getBytes());
//         Mockito.when(httpExchange.getRequestBody()).thenReturn(inputStream);

//         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//         Mockito.when(httpExchange.getResponseBody()).thenReturn(outputStream);

//         requestHandler.handle(httpExchange);

//         String response = outputStream.toString();
//         assertEquals("Posted entry {Java, 1995}", response);
//     }

//     @Test
//     void testHandleDelete() throws Exception {
//         Map<String, String> data = new HashMap<>();
//         data.put("Java", "1995");

//         RequestHandler requestHandler = new RequestHandler(data);
//         HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
//         URI uri = new URI("/delete?language=Java");
//         Mockito.when(httpExchange.getRequestMethod()).thenReturn("DELETE");
//         Mockito.when(httpExchange.getRequestURI()).thenReturn(uri);

//         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//         Mockito.when(httpExchange.getResponseBody()).thenReturn(outputStream);

//         requestHandler.handle(httpExchange);

//         String response = outputStream.toString();
//         assertEquals("Deleted entry {Java, 1995}", response);
//     }

//     @Test
//     void testHandlePut() throws Exception {
//         Map<String, String> data = new HashMap<>();
//         data.put("Java", "1995");

//         RequestHandler requestHandler = new RequestHandler(data);
//         HttpExchange httpExchange = Mockito.mock(HttpExchange.class);
//         Mockito.when(httpExchange.getRequestMethod()).thenReturn("PUT");

//         InputStream inputStream = new ByteArrayInputStream("Java,2022".getBytes());
//         Mockito.when(httpExchange.getRequestBody()).thenReturn(inputStream);

//         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//         Mockito.when(httpExchange.getResponseBody()).thenReturn(outputStream);

//         requestHandler.handle(httpExchange);

//         String response = outputStream.toString();
//         assertEquals("Updated entry {Java, 2022} (previous year: 1995)", response);
//     }
// }
