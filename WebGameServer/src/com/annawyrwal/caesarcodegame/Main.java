package com.annawyrwal.caesarcodegame;

import com.annawyrwal.caesarcodegame.serverside.Listener;
import java.io.*;
import java.util.Properties;

/*****************
 * @Author Anna Wyrwał
 */

public class Main {

    public static void main(String[] args) {
        initializeServer(args);
    }

    private static void initializeServer(String[] args) {
        Properties props = loadConfig();

        String port = props.getProperty("PortNumber");
        String rounds = props.getProperty("RoundsNumber");
        int portNumber = 0;
        int numberOfRounds = 0;

        try {
            portNumber = Integer.parseInt(port);
            numberOfRounds = Integer.parseInt(rounds);
        } catch (NumberFormatException ex) {
            System.err.println("Cannot parse config values into integers. Fix config and try again.");
            System.exit(-1);
        }

        new Listener(portNumber, numberOfRounds).start();
    }

    private static Properties loadConfig() {
        File configFile = new File("src/com/annawyrwal/caesarcodegame/conf.xml");

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();

        try {
            props.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props;
    }


}
