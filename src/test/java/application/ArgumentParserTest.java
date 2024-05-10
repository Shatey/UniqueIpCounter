package application;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {

    @Test(expected = NullPointerException.class)
    public void testNullArguments() {
        ArgumentParser parser = new ArgumentParser();
        parser.parseArguments(null);
    }

    @Test
    public void testInvalidArgumentsLength() {
        ArgumentParser parser = new ArgumentParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parseArguments(new String[0]));
        assertThrows(IllegalArgumentException.class, () -> parser.parseArguments(new String[]{"filename", "-file"}));
    }

    @Test
    public void testInvalidArgumentsFormat() {
        ArgumentParser parser = new ArgumentParser();
        try {
            parser.parseArguments(new String[]{"filename", "file"});
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid arguments. Usage: -file <filename>", e.getMessage());
        }
    }

    @Test
    public void testValidArguments() {
        ArgumentParser parser = new ArgumentParser();
        String[] args = new String[]{"-file", "filename"};
        String filename = parser.parseArguments(args);
        assertNotNull(filename);
        assertEquals("filename", filename);
    }

    @Test
    public void testEmptyFileName() {
        ArgumentParser parser = new ArgumentParser();
        String[] args = new String[]{"-file", ""};
        try {
            parser.parseArguments(args);
            fail("Expected an IllegalArgumentException to be thrown, but it wasn't.");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid arguments. Usage: -file <filename>", e.getMessage());
        }
    }
}