package com.annawyrwal.caesarcodegame.caesarcode;

import static java.lang.Math.abs;

public class CaesarCipher {
    public static final String ALPHABET = "aąbcćdeęfghijklłmnńoóprsśtuwyzźż";
    public static final int ALPHABET_LENGTH = 32;

    public static String encrypt(String plainText, int shiftKey) {
        plainText = plainText.toLowerCase();
        String cipherText = "";
        try {
            for (int i = 0; i < plainText.length(); i++) {
                int charPosition = ALPHABET.indexOf(plainText.charAt(i));
                int keyVal = (shiftKey + charPosition) % ALPHABET_LENGTH;
                char replaceVal = ALPHABET.charAt(abs(keyVal));
                cipherText += replaceVal;
            }
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println(ex.getCause());
            ex.printStackTrace();
        }
        return cipherText;
    }

    public static String decrypt(String cipherText, int shiftKey) {
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for (int i = 0; i < cipherText.length(); i++) {
            int charPosition = ALPHABET.indexOf(cipherText.charAt(i));
            int keyVal = (charPosition - shiftKey) % ALPHABET_LENGTH;
            if (keyVal < 0) {
                keyVal = ALPHABET.length() + keyVal;
            }
            char replaceVal = ALPHABET.charAt(keyVal);
            plainText += replaceVal;
        }
        return plainText;
    }
}