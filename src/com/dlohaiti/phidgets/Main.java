package com.dlohaiti.phidgets;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {

    static class Message extends Thread {
        public void run() {
            System.out.println("Exiting...");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting program");
        Runtime.getRuntime().addShutdownHook(new Message());
        Properties properties = new Properties();
        properties.load(new FileInputStream("./data.properties"));

        PhidgetsReader phidgetsReader = new PhidgetsReader(properties);
        phidgetsReader.join();
        System.out.println("Stopping program");

    }
}
