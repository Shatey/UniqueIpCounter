package application;

import processing.IpCounter;

public class IpCounterApplication {
    private final ArgumentParser argumentParser;
    private final IpCounter ipCounter;

    public IpCounterApplication() {
        this.argumentParser = new ArgumentParser();
        this.ipCounter = new IpCounter();
    }

    public long run(String[] args) {
        String fileName = argumentParser.parseArguments(args);
        if (fileName != null) {
            long uniqueIpCount = ipCounter.countUniqueIps(fileName);
            if (uniqueIpCount >= 0) {
                System.out.println("Found " + uniqueIpCount + " unique IP addresses.");
            } else {
                System.out.println("An error occurred while counting unique IPs. Please check the logs for details.");
            }
            return uniqueIpCount;
        }
        return -1;
    }
}