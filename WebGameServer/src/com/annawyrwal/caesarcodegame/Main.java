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
        if (args.length != 1) {
            System.err.println("You should pass <port number>");
            System.exit(-1);
        }

        int portNumber = Integer.parseInt(args[0]);
        Listener listener = new Listener(portNumber);
        listener.start();
    }
}
