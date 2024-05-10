package app;

import application.IpCounterApplication;

public class IpCounterApp {

    public static void main(String[] args) {
        IpCounterApplication app = new IpCounterApplication();
        long uniqueIpCount = app.run(args);
    }
}