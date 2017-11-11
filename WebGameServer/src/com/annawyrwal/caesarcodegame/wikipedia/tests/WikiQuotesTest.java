package com.annawyrwal.caesarcodegame.wikipedia.tests;

import com.annawyrwal.caesarcodegame.wikipedia.WikiQuotes;
import org.junit.Test;


import static org.junit.Assert.*;


public class WikiQuotesTest {
    @Test
    public void getRandomQuote() {
        WikiQuotes wikiQuotes = new WikiQuotes();
        int testLength = 20;

        String text = wikiQuotes.getRandomQuote(testLength);
        assertNotNull(text);
    }
}
