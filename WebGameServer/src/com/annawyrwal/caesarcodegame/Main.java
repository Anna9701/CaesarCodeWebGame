package com.annawyrwal.caesarcodegame;

import com.annawyrwal.caesarcodegame.serverside.Listener;

/*****************
 * @Author Anna Wyrwał
 */

public class Main {

    public static void main(String[] args) {
        initializeServer(args);
    }

    private static void initializeServer(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: <port number> <number of rounds>");
            System.exit(-1);
        }

        try {
            int portNumber = Integer.parseInt(args[0]);
            int numberOfRounds;
            Listener listener;
            if (args.length == 2) {
                numberOfRounds = Integer.parseInt(args[1]);
                listener = new Listener(portNumber, numberOfRounds);
            } else
                listener = new Listener(portNumber);

            listener.start();
        } catch (NumberFormatException ex) {
            System.err.println("Usage: <port number> <number of rounds>");
            System.exit(-1);
        }

    }
}
