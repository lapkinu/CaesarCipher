package com.javarush.caesarcipher.lapkin.function;

import com.javarush.caesarcipher.lapkin.handler.FileHandler;
import com.javarush.caesarcipher.lapkin.handler.TextHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.javarush.caesarcipher.lapkin.handler.TextDecryption.*;

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
    public static void Crypt(String inputFilePathEncrypt, String inputFilePathReference,
                             String decryptedOutputPath) throws IOException {
        try {
            String textEncrypt = FileHandler.readFile(inputFilePathEncrypt);
            String textReference = FileHandler.readFile(inputFilePathReference);
            Map<Character, Integer> freqMapEncryptText = getCharFrequency(textEncrypt);
            Map<Character, Integer> freqMapReferenceText = getCharFrequency(textReference);
            List<Character> sortListCharsEncryptText = sortCharsByFrequency(freqMapEncryptText);
            List<Character> sortListCharsReferenceText = sortCharsByFrequency(freqMapReferenceText);
            String replaced = replaceCharsInText(textEncrypt, sortListCharsEncryptText, sortListCharsReferenceText);
            FileHandler.writeFile(decryptedOutputPath, replaced);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
