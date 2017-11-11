package com.annawyrwal.caesarcodegame.serverside.tests;

import com.annawyrwal.caesarcodegame.serverside.Ranking;
import org.junit.Test;


import static org.junit.Assert.*;


public class RankingTest {

    @Test
    public void getRanking() throws Exception {
        Ranking ranking = new Ranking();

        String rankingString = ranking.getRanking();

        assertNotNull(rankingString);
    }

}
