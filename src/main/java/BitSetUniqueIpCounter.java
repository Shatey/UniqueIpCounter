import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.logging.Logger;

public class BitSetUniqueIpCounter {

    /**
     * Total number of possible IP addresses (using signed long for compatibility).
     */
    private static final long TOTAL_IP_ADDRESSES = 256L * 256 * 256 * 256;

    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Two BitSets are used to cover the full range of IP addresses as Java's BitSet
     * has a maximum size of Integer.MAX_VALUE.
     */
    private final BitSet ipSetLow = new BitSet(Integer.MAX_VALUE); // 0 - 2_147_483_647
    private final BitSet ipSetHi = new BitSet(Integer.MAX_VALUE); // 2_147_483_648 - 4_294_967_295
    private long uniqueIpCount = 0;

    /**
     * Registers a given IP address (as a long value) in the appropriate BitSet.
     *
     * @param ipValue The IP address as a long value.
     */
    private void registerIp(long ipValue) {
        int intValue = ipValue > Integer.MAX_VALUE ?
                (int) (ipValue - Integer.MAX_VALUE) : (int) ipValue;
        BitSet workingSet = ipValue > Integer.MAX_VALUE ? ipSetHi : ipSetLow;
        if (!workingSet.get(intValue)) {
            uniqueIpCount++;
            workingSet.set(intValue);
        }
    }

    /**
     * Counts the number of unique IP addresses in a given file.
     *
     * @param fileName The path to the file containing IP addresses.
     * @return The number of unique IP addresses found, or -1 if an error occurs.
     */
    public long countUniqueIps(String fileName) {
        logger.info("Starting to read file: " + fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            long linesProcessed = 0;
            String line;
            while ((line = reader.readLine()) != null && uniqueIpCount <= TOTAL_IP_ADDRESSES) {
                registerIp(ipStringToLong(line));
                linesProcessed++;
            }
            logger.info("Total lines processed: " + linesProcessed);
        } catch (IOException e) {
            logger.warning("Error occurred while reading file: " + fileName + "\nError: " + e);
            uniqueIpCount = -1;
        }
        return uniqueIpCount;
    }

    /**
     * Converts an IP address string to a long value for internal processing.
     *
     * @param ipString The IP address string.
     * @return The IP address as a long value.
     */
    private long ipStringToLong(String ipString) {
        long result = 0;
        for (String field : ipString.split("\\.")) {
            result = (result << 8) + Integer.parseInt(field);
        }
        return result;
    }
}
