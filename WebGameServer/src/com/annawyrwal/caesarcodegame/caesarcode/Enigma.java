package com.annawyrwal.caesarcodegame.caesarcode;

import java.util.Random;

public class Enigma {
    private final int key;
    private Random random;
    private final String cryptedText;
    private final String decryptedText;
    private final int maxKey = 1000;

    public Enigma (String text) {
        random = new Random();
        key = random.nextInt(maxKey);
        cryptedText = CaesarCipher.encrypt(text, key);
        decryptedText = text;
    }

    public int getKey() {
        return key;
    }

    public String getCryptedText() {
        return cryptedText;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public boolean isValid (String text) {
        return text.equalsIgnoreCase(decryptedText);
    }

}
