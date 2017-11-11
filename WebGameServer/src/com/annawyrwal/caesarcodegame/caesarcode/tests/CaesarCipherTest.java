package com.annawyrwal.caesarcodegame.caesarcode.tests;

import com.annawyrwal.caesarcodegame.caesarcode.CaesarCipher;
import org.junit.Test;

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
