package application;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class IpCounterApplicationTest {

    @Test
    public void testCountUniqueIps() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.1", "1.1.1.1", "255.255.255.255"));
        tempFile.deleteOnExit();

        IpCounterApplication app = new IpCounterApplication();

        long uniqueIpCount = app.run(new String[]{"-file", tempFile.getAbsolutePath()});
        assertEquals(5, uniqueIpCount);
    }

    @Test
    public void testInvalidArguments() {
        IpCounterApplication ipCounterApplication = new IpCounterApplication();

        assertThrows(IllegalArgumentException.class, () -> ipCounterApplication.run(new String[0]));
        assertThrows(IllegalArgumentException.class, () -> ipCounterApplication.run(new String[]{"filename", "-file"}));
        try {
            ipCounterApplication.run(new String[]{"filename", "-file"});
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid arguments. Usage: -file <filename>", e.getMessage());
        }
    }

    @Test
    public void testFileDoesNotExist() {
        IpCounterApplication ipCounterApplication = new IpCounterApplication();
        String[] args = new String[]{"-file", "non-existent-file.txt"};

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ipCounterApplication.run(args);
        });

        assertEquals("java.io.FileNotFoundException: non-existent-file.txt (Не удается найти указанный файл)", exception.getMessage());
    }
}