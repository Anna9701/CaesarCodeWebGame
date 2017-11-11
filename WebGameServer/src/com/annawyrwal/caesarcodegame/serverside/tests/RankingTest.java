package com.annawyrwal.caesarcodegame.serverside.tests;

import com.annawyrwal.caesarcodegame.serverside.Listener;
import com.annawyrwal.caesarcodegame.serverside.Ranking;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


public class RankingTest {

    @Test
    public void getRanking() throws Exception {
        Ranking ranking = new Ranking();

        String rankingString = ranking.getRanking();

        assertNotNull(rankingString);
    }

}
