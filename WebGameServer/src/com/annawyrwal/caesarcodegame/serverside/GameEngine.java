package com.annawyrwal.caesarcodegame.serverside;

import com.annawyrwal.caesarcodegame.caesarcode.Enigma;
import com.annawyrwal.caesarcodegame.wikipedia.WikiQuotes;

import java.util.ArrayList;

public class GameEngine {
    private int numberOfRounds;
    private static ArrayList<Enigma> enigmas;
    private WikiQuotes wikiQuotes;
    private ArrayList<Integer> lengths;
    private static int currentRound;
    private Ranking ranking;

    public GameEngine(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
        ranking = new Ranking();
        enigmas = new ArrayList<>();
        lengths = new ArrayList<>();
        wikiQuotes = new WikiQuotes();
        initializeLengths();
        initializeEnigmas();
        currentRound = 0;
    }


    public void addUserToRanking(Listener.Client client) {
        ranking.addClientToRanking(client);
    }

    public Ranking getRanking() {
        return ranking;
    }

    public int getCurrentRound() {
        return currentRound + 1;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    private int getNumberOfPointsForCurrentRound() {
        return (currentRound + 1) * 10;
    }

    public void addPointsForCorrectAnswer(Listener.Client client) {
        ranking.addPointToCliet(client, getNumberOfPointsForCurrentRound());
    }

    public void resetGame() {
        currentRound = 0;
        enigmas = new ArrayList<>();
        initializeEnigmas();
    }

    public void resetGame(int numberOfRounds) {
        currentRound = 0;
        this.numberOfRounds = numberOfRounds;
        lengths = new ArrayList<>();
        enigmas = new ArrayList<>();
        initializeLengths();
        initializeEnigmas();
    }

    private void initializeLengths() {
        for (int i = 0; i < numberOfRounds; i++)
            lengths.add((i+1) * 15);
    }

    private void initializeEnigmas() {
        for (int i = 0; i < numberOfRounds; i++) {
            final int j = i;
            new Thread( () -> enigmas.add(new Enigma(wikiQuotes.getRandomQuote(lengths.get(j))))).run();
        }
    }

    public boolean nextRound() {
        if (++currentRound == numberOfRounds)
            return false;
        return true;
    }

    public static Enigma getEnigma() {
        return enigmas.get(currentRound);
    }
}
