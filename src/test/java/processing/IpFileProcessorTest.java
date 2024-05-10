package processing;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class IpFileProcessorTest {

    private IpFileProcessor fileProcessor;

    @Before
    public void setup() {
        fileProcessor = new IpFileProcessor();
    }

    @Test
    public void testEmptyFile() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        tempFile.deleteOnExit();

        Iterable<Long> ipAddresses = fileProcessor.processFile(tempFile.getAbsolutePath());

        Iterator<Long> iterator = ipAddresses.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testSingleIp() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), List.of("192.168.1.1"));
        tempFile.deleteOnExit();

        Iterable<Long> ipAddresses = fileProcessor.processFile(tempFile.getAbsolutePath());

        Iterator<Long> iterator = ipAddresses.iterator();
        for (Long ipAddress : ipAddresses) {
            assertTrue(iterator.hasNext());
            assertEquals(ipAddress, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testMultipleIps() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.1"));
        tempFile.deleteOnExit();

        Iterable<Long> ipAddresses = fileProcessor.processFile(tempFile.getAbsolutePath());

        Iterator<Long> iterator = ipAddresses.iterator();
        for (Long ipAddress : ipAddresses) {
            assertTrue(iterator.hasNext());
            assertEquals(ipAddress, iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testInvalidFile() {
        String invalidFileName = "non-existent-file.txt";

        Iterable<Long> ipAddresses = fileProcessor.processFile(invalidFileName);
        RuntimeException exception = assertThrows(RuntimeException.class, ipAddresses::iterator);

        assertEquals("java.io.FileNotFoundException: non-existent-file.txt (Не удается найти указанный файл)", exception.getMessage());

    }
}