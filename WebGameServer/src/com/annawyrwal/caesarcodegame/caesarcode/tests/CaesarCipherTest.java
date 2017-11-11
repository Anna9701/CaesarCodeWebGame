package com.annawyrwal.caesarcodegame.caesarcode.tests;

import com.annawyrwal.caesarcodegame.caesarcode.CaesarCipher;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class CaesarCipherTest {

    @Test
    public void encrypt() {
        String testString = "test";
        int testKey = 3;
        String result = CaesarCipher.encrypt(testString, testKey);
        assertNotEquals(testString, result);
    }

    @Test
    public void decrypt() {
        String testString = "yguy"; //  "test" with key 3
        int testKey = 3;
        String resultExpected = "test";
        String result = CaesarCipher.decrypt(testString, testKey);
        assertEquals(resultExpected, result);
    }

   

}
