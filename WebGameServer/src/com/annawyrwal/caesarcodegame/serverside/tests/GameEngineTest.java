package com.annawyrwal.caesarcodegame.serverside.tests;

import com.annawyrwal.caesarcodegame.caesarcode.Enigma;
import com.annawyrwal.caesarcodegame.serverside.GameEngine;
import com.annawyrwal.caesarcodegame.serverside.Ranking;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class GameEngineTest {
    @Test
    public void getRanking()  {
        int testRoundsNumber = 3;
        GameEngine gameEngine = new GameEngine(testRoundsNumber);
        Ranking ranking = gameEngine.getRanking();

        assertNotNull(ranking);
    }

    @Test
    public void getNumberOfRounds() {
        int testRoundsNumber = 3;
        GameEngine gameEngine = new GameEngine(testRoundsNumber);

        int actualRoundNumber = gameEngine.getNumberOfRounds();
        assertEquals(testRoundsNumber, actualRoundNumber);
    }

    @Test
    public void nextRound() {
        int testRoundsNumber = 3;
        GameEngine gameEngine = new GameEngine(testRoundsNumber);

        int expectedNumber = gameEngine.getCurrentRound() + 1;
        gameEngine.nextRound();
        int actualNumber = gameEngine.getCurrentRound();
        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    public void getEnigma() {
        int testRoundsNumber = 3;
        GameEngine gameEngine = new GameEngine(testRoundsNumber);

        Enigma enigma = gameEngine.getEnigma();
        assertNotNull(enigma);
    }

}
