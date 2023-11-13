import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ChatGPT;

import java.io.IOException;
import java.net.URISyntaxException;

public class ChatGPTTest {

    private ChatGPT chatGPT;

    @BeforeEach
    public void setUp() {
        chatGPT = mock(ChatGPT.class);
    }

    @Test
    public void testGenerate() throws IOException, InterruptedException, URISyntaxException {
        String prompt = "Where is UC San Diego located?";
        String location = "9500 Gilman Dr, La Jolla, CA 92093";

        when(chatGPT.generate(anyString())).thenReturn(location);
        String generatedText = chatGPT.generate(prompt);

        assertNotNull(generatedText);
        assertEquals(location, generatedText);

        System.out.println("Generated Text: " + generatedText);
    }
}
