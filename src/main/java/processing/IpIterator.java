package processing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * This class implements an iterator to read IP addresses from a file.
 */
public class IpIterator implements Iterator<Long> {
    private final BufferedReader reader;
    private String nextLine;

    /**
     * Creates a new IpIterator for the given file.
     *
     * @param fileName The name of the file to read.
     */
    public IpIterator(String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            // Read the first line to initialize the nextLine variable.
            nextLine = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public Long next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        try {
            Long ipAddress = ipStringToLong(nextLine);
            nextLine = reader.readLine();
            return ipAddress;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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