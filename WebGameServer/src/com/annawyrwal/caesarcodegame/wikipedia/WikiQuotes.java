package com.annawyrwal.caesarcodegame.wikipedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.annawyrwal.caesarcodegame.wikipedia.Wiki.createInstance;

public class WikiQuotes {
    private ArrayList<String> authors;
    private final Wiki wiki;

    public WikiQuotes() {
        authors = new ArrayList<>();
        addAuthorsToList();
        wiki = createInstance("pl.wikiquote.org/");
    }

    private void addAuthorsToList() {
        authors.add("Leonardo_da_Vinci");
        authors.add("Juliusz_Cezar");
        authors.add("Pitagoras");
        authors.add("Plutarch");
        authors.add("Galileusz");
        authors.add("John_Ronald_Reuel_Tolkien");
        authors.add("Carl_Gustav_Jung");
        authors.add("William_Shakespeare");
        authors.add("George_Byron");
        authors.add("Aleksander_Wielki");
        authors.add("Umberto_Eco");
        authors.add("Homer");
    }

    private String getRandomAuthor() {
        int index;
        Random random = new Random();
        index = random.nextInt(authors.size() - 1);

        return authors.get(index);
    }

    public String getRandomQuote(int maxLength) {
        Random random = new Random();
        ArrayList<String> quotes;

        do {
            quotes = getRandomQuotes();
            quotes = getProperLengthQuotes(maxLength, quotes);
        } while (quotes.size() == 0);

        if (quotes.size() > 2)
            return quotes.get(random.nextInt(quotes.size() - 1));
        return quotes.get(0);
    }

    ArrayList<String> getProperLengthQuotes(int maxLength, ArrayList<String> quotes) {
        ArrayList<String> resultList = new ArrayList<>();
        for (String s : quotes)
            if (s.length() <= maxLength)
                resultList.add(s.replaceAll("[^\\p{IsAlphabetic}^\\p{IsDigit}]", "").toLowerCase());

        return resultList;
    }

    private ArrayList<String> getRandomQuotes () {
        String author = getRandomAuthor();
        String text = "";
        try {
            text = wiki.getPageText(author);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> list = findQuotesInText(text);

        return list;
    }

    private ArrayList<String> findQuotesInText(String text) {
        ArrayList<String> quotesWithStars = new ArrayList<>();
        ArrayList<String> quotes = new ArrayList<>();
        String[] lines = text.split("\\R");
        for (String s : lines) {
            if (s.startsWith("*"))
                if(!s.contains("Zobacz też: [[") && !s.startsWith("** Autor: ") && !s.startsWith("** Opis:") && !s.startsWith("** Źródło: ") && !s.startsWith("[[") && !s.startsWith("** Postać:"))
                    quotesWithStars.add(s);
        }

        for (String s : quotesWithStars) {
            while (s.charAt(0) == '*' || s.charAt(0) == ' ' || s.charAt(0) == '(' || s.charAt(0) == ')' || s.charAt(0) == '.') {
                s = s.substring(1, s.length());
            }
            quotes.add(s);
        }


        return quotes;
    }
}
