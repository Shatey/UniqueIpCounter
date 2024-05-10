package processing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IpSetTest {

    private IpSet ipSet;

    @Before
    public void setup() {
        ipSet = new IpSet();
    }

    @Test
    public void testAddIpAddressInRange() {
        long ipAddress = 1921680001L; // 192.168.0.1
        ipSet.add(ipAddress);
        assertTrue(ipSet.isExists(ipAddress));
    }

    @Test
    public void testAddIpAddressOutOfRange() {
        long ipAddress = 4294967295L; // 255.255.255.255
        ipSet.add(ipAddress);
        assertTrue(ipSet.isExists(ipAddress));
    }

    @Test
    public void testIsExistsIpAddressDoesNotExist() {
        long ipAddress = 1921680001L; // 192.168.0.1
        assertFalse(ipSet.isExists(ipAddress));
    }

    @Test
    public void testIsExistsIpAddressExists() {
        long ipAddress = 1921680001L; // 192.168.0.1
        ipSet.add(ipAddress);
        assertTrue(ipSet.isExists(ipAddress));
    }

    @Test
    public void testAddMultipleIpAddresses() {
        long ipAddress1 = 1921680001L; // 192.168.0.1
        long ipAddress2 = 1921680002L; // 192.168.0.2
        ipSet.add(ipAddress1);
        ipSet.add(ipAddress2);
        assertTrue(ipSet.isExists(ipAddress1));
        assertTrue(ipSet.isExists(ipAddress2));
    }
}