package app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class IpCounterAppTest {

    @Test
    public void testMainException() {
        assertThrows(IllegalArgumentException.class, () -> IpCounterApp.main(new String[0]));
        assertThrows(IllegalArgumentException.class, () -> IpCounterApp.main(new String[]{"filename", "-file"}));
        try {
            IpCounterApp.main(new String[]{"filename", "-file"});
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid arguments. Usage: -file <filename>", e.getMessage());
        }
    }
}