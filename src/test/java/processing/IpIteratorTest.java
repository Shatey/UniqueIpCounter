package processing;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class IpIteratorTest {

    private IpIterator ipIterator;

    @Before
    public void setup() {
    }

    @Test
    public void testEmptyFile() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        tempFile.deleteOnExit();

        ipIterator = new IpIterator(tempFile.getAbsolutePath());

        assertFalse(ipIterator.hasNext());
    }

    @Test
    public void testSingleIp() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), List.of("192.168.1.1"));
        tempFile.deleteOnExit();

        ipIterator = new IpIterator(tempFile.getAbsolutePath());

        assertTrue(ipIterator.hasNext());
        long next = ipIterator.next();
        assertEquals(3232235777L, next);
        assertFalse(ipIterator.hasNext());
    }

    @Test
    public void testMultipleIps() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        Files.write(Paths.get(tempFile.getAbsolutePath()), Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3"));
        tempFile.deleteOnExit();

        ipIterator = new IpIterator(tempFile.getAbsolutePath());

        assertTrue(ipIterator.hasNext());
        long next = ipIterator.next();
        assertEquals(3232235777L, next);
        assertTrue(ipIterator.hasNext());
        next = ipIterator.next();
        assertEquals(3232235778L, next);
        assertTrue(ipIterator.hasNext());
        next = ipIterator.next();
        assertEquals(3232235779L, next);
        assertFalse(ipIterator.hasNext());
    }

    @Test
    public void testInvalidFile() {
        String invalidFileName = "non-existent-file.txt";

        try {
            ipIterator = new IpIterator(invalidFileName);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testNoSuchElementException() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        tempFile.deleteOnExit();

        ipIterator = new IpIterator(tempFile.getAbsolutePath());

        try {
            ipIterator.next();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }
}