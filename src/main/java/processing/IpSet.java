package processing;

import java.util.BitSet;

/**
 * This class represents a set of unique IP addresses.
 */
public class IpSet {

    /**
     * A BitSet to store IP addresses in the range 0 - 2_147_483_647.
     */
    private final BitSet ipSetLow = new BitSet(Integer.MAX_VALUE);

    /**
     * A BitSet to store IP addresses in the range 2_147_483_648 - 4_294_967_295.
     */
    private final BitSet ipSetHi = new BitSet(Integer.MAX_VALUE);

    /**
     * Adds an IP address to the set.
     *
     * @param ipAddress The IP address to add.
     */
    public void add(long ipAddress) {
        BitSet workingSet = chooseBitset(ipAddress);
        if (ipAddress > Integer.MAX_VALUE) {
            /*
            Integer maximum is 2147483647. Next num is 2147483648 - Integer.MAX_VALUE is 1.
            255.255.255.255 as long is 4294967295 - Integer.MAX_VALUE is also 2147483648
            and after converting it to int we have -2147483648. So in this case I replace it with 0 and put it into IpSetHi
            because 0 is empty in IpSetHi
             */
            int intValue = (int) (ipAddress - Integer.MAX_VALUE);
            workingSet.set(Math.max(intValue, 0));
        } else {
            workingSet.set((int) ipAddress);
        }
    }

    /**
     * Checks if an IP address exists in the set.
     *
     * @param ipAddress The IP address to check.
     * @return True if the IP address exists, false otherwise.
     */
    public boolean isExists(long ipAddress) {
        BitSet workingSet = chooseBitset(ipAddress);
        if (ipAddress > Integer.MAX_VALUE) {
            int intValue = (int) (ipAddress - Integer.MAX_VALUE);
            return workingSet.get(Math.max(intValue, 0));
        }
        return workingSet.get((int) ipAddress);
    }

    /**
     * Chooses the correct BitSet based on the IP address range.
     *
     * @param ipAddress The IP address to check.
     * @return The correct BitSet.
     */
    private BitSet chooseBitset(long ipAddress) {
        return ipAddress > Integer.MAX_VALUE ? ipSetHi : ipSetLow;
    }
}