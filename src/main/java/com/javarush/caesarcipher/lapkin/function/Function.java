package com.javarush.caesarcipher.lapkin.function;

import com.javarush.caesarcipher.lapkin.handler.FileHandler;
import com.javarush.caesarcipher.lapkin.handler.TextHandler;

import java.io.IOException;

public class Function { 
    public static void encrypt (String inputFilePath, String encryptedOutputPath, int keyShift ) {
        try {
            String originalText = FileHandler.readFile(inputFilePath);
            String encryptedText = TextHandler.encryptByKey(originalText, keyShift);
            FileHandler.writeFile(encryptedOutputPath, encryptedText);
        } catch (
                IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public static void decrypt(String inputFilePath, String decryptedOutputPath, int keyShift ) {
        try {
            String encryptedText = FileHandler.readFile(inputFilePath);
            String decryptedText = TextHandler.decryptByKey(encryptedText, keyShift);
            FileHandler.writeFile(decryptedOutputPath, decryptedText);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public static void decryptBruteForce(String inputFilePath, String decryptedOutputPath, String regex) {
        try {
            String encryptedText = FileHandler.readFile(inputFilePath);
            String regexDecryptedText = TextHandler.decryptBruteForceWithRegex(encryptedText, regex);
            FileHandler.writeFile(decryptedOutputPath, regexDecryptedText);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }




}
