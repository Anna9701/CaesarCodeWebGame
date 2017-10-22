package com.annawyrwal.caesarcodegame;

import com.annawyrwal.caesarcodegame.wikipedia.WikiQuotes;
import com.annawyrwal.caesarcodegame.serverside.Listener;

public class Main {

    public static void main(String[] args) {
        initializeServer(args);

        WikiQuotes wikiQuotes = new WikiQuotes();
        System.out.println(wikiQuotes.getRandomQuote(40));
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
