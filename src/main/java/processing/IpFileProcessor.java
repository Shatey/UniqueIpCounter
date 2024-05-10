package processing;

/**
 * This class is responsible for processing a file containing IP addresses.
 */
public class IpFileProcessor {

    /**
     * This method processes a file and returns an iterable of IP addresses.
     *
     * @param fileName The path to the file containing IP addresses.
     * @return An iterable of IP addresses.
     */
    public Iterable<Long> processFile(String fileName) {
        return () -> new IpIterator(fileName);
    }
}