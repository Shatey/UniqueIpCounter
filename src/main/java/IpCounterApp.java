
public class IpCounterApp {

    public static void main(String[] args) throws IllegalArgumentException {
        String fileName = validateAndGetFileName(args);
        BitSetUniqueIpCounter counter = new BitSetUniqueIpCounter();
        long uniqueIpCount = counter.countUniqueIps(fileName);
        if (uniqueIpCount >= 0) {
            System.out.println("Found " + uniqueIpCount + " unique IP addresses.");
        } else {
            System.out.println("An error occurred while counting unique IPs. Please check the logs for details.");
        }
    }

    /**
     * Parses the command-line arguments and extracts the file name.
     *
     * @param args The command-line arguments.
     * @return The file name specified in the arguments, or null if invalid.
     * @throws NullPointerException if the `args` parameter is null.
     */
    private static String validateAndGetFileName(String[] args) {
        if (args.length != 2 || !args[0].equals("-file")) {
            throw new IllegalArgumentException("Invalid arguments. Usage: -file <filename>");
        }
        return args[1];
    }
}