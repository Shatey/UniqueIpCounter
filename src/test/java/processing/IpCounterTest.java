package processing;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class IpCounterTest {

    private IpCounter ipCounter;

    @Before
    public void setup() {
        ipCounter = new IpCounter();
    }

    @Test
    public void testEmptyFile() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        tempFile.deleteOnExit();

        long uniqueIpCount = ipCounter.countUniqueIps(tempFile.getAbsolutePath());

        assertEquals(0, uniqueIpCount);
    }

    @Test
    public void testSingleIp() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), List.of("127.255.255.255"));
        tempFile.deleteOnExit();

        long uniqueIpCount = ipCounter.countUniqueIps(tempFile.getAbsolutePath());

        assertEquals(1, uniqueIpCount);
    }

    @Test
    public void testMultipleIps() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.1"));
        tempFile.deleteOnExit();

        long uniqueIpCount = ipCounter.countUniqueIps(tempFile.getAbsolutePath());

        assertEquals(3, uniqueIpCount);
    }

    @Test
    public void testInvalidFile() {
        String invalidFileName = "non-existent-file.txt";
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ipCounter.countUniqueIps(invalidFileName);
        });

        assertEquals("java.io.FileNotFoundException: non-existent-file.txt (Не удается найти указанный файл)", exception.getMessage());

    }
}