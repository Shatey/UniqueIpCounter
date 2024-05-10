package processing;

/**
 * This class counts the number of unique IP addresses in a file.
 */
public class IpCounter {
    private final IpSet ipSet;

    public IpCounter() {
        this.ipSet = new IpSet();
    }

    /**
     * Counts the number of unique IP addresses in a file.
     *
     * @param fileName The name of the file to process.
     * @return The number of unique IP addresses in the file.
     */
    public long countUniqueIps(String fileName) {
        IpFileProcessor fileProcessor = new IpFileProcessor();
        Iterable<Long> ipAddresses = fileProcessor.processFile(fileName);
        long uniqueIpCount = 0;

        for (long ipAddress : ipAddresses) {
            if (!ipSet.isExists(ipAddress)) {
                uniqueIpCount++;
                ipSet.add((ipAddress));
            }
        }
        return uniqueIpCount;
    }
}