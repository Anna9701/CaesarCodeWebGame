package com.annawyrwal.caesarcodegame.wikipedia.tests;

import com.annawyrwal.caesarcodegame.wikipedia.WikiQuotes;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

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
