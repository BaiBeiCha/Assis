package org.baibei.assis.services;

import org.springframework.stereotype.Service;

@Service
public class CryptService {

    private int scale = 10;
    private int difference = 5;

    public String encrypt(String plainText) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);
            char newChar = (char) ((currentChar + difference) * scale);
            encryptedText.append(newChar);
        }
        return encryptedText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char currentChar = cipherText.charAt(i);
            char newChar = (char) ((currentChar / scale) - difference);
            encryptedText.append(newChar);
        }
        return encryptedText.toString();
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public int getScale() {
        return scale;
    }

    public int getDifference() {
        return difference;
    }
}
