import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URISyntaxException;

public class ChatGPTTest {

    private ChatGPT chatGPT;

    @BeforeEach
    public void setUp() {
        chatGPT = new ChatGPT();
    }

    @Test
    public void testGenerate() throws IOException, InterruptedException, URISyntaxException {
        String prompt = "Where is UC San Diego located?";
        String generatedText = chatGPT.generate(prompt);

        assertNotNull(generatedText);
        assertFalse(generatedText.isEmpty());

        System.out.println("Generated Text: " + generatedText);
    }
}
