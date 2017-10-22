package com.annawyrwal.caesarcodegame;

import com.annawyrwal.caesarcodegame.wikipedia.WikiQuotes;

public class Main {

    public static void main(String[] args) {
        WikiQuotes wikiQuotes = new WikiQuotes();
        System.out.println(wikiQuotes.getRandomQuote(40));
    }
}
