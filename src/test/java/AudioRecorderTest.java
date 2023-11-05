import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sound.sampled.*;
import java.io.File;

public class AudioRecorderTest {
    public AudioRecorder audioRecorder;

    @BeforeEach
    public void setUp() {
        audioRecorder = new AudioRecorder();
    }

    // @Test
    // public void testStartRecording() {
    //     AudioRecorder mockedAudioRecorder = Mockito.spy(audioRecorder);
    //     AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);

    //     // Mock the TargetDataLine
    //     TargetDataLine targetDataLine = Mockito.mock(TargetDataLine.class);

    //     try {
    //         Mockito.when(AudioSystem.getLine(Mockito.any())).thenReturn(targetDataLine);

    //         mockedAudioRecorder.startRecording();

    //         assertTrue(mockedAudioRecorder.isRecording);

    //         // Verify that the TargetDataLine was opened and started
    //         Mockito.verify(targetDataLine).open(audioFormat);
    //         Mockito.verify(targetDataLine).start();
    //     } catch (LineUnavailableException e) {
    //         fail("LineUnavailableException should not be thrown");
    //     }
    // }

    // @Test
    // public void testStopRecording() {
    //     AudioRecorder mockedAudioRecorder = Mockito.spy(audioRecorder);
    //     mockedAudioRecorder.isRecording = true;

    //     // Mock the TargetDataLine
    //     TargetDataLine targetDataLine = Mockito.mock(TargetDataLine.class);
    //     mockedAudioRecorder.targetDataLine = targetDataLine;

    //     mockedAudioRecorder.stopRecording();

    //     assertFalse(mockedAudioRecorder.isRecording);

    //     // Verify that the TargetDataLine was stopped and closed
    //     Mockito.verify(targetDataLine).stop();
    //     Mockito.verify(targetDataLine).close();
    // }
}
