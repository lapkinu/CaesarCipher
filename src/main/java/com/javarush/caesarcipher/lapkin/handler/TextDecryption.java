package com.javarush.caesarcipher.lapkin.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextDecryption {
    private static long finalTime;
    private static long startTime;
    public static Map<Character, Integer> getCharFrequency(String str) {
        startTime = System.nanoTime();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : str.toCharArray()) {
            if (frequencyMap.containsKey(ch)) {
                frequencyMap.put(ch, frequencyMap.get(ch) + 1);
            } else {
                frequencyMap.put(ch, 1);
            }
        }
        return frequencyMap;
    }
    public static List<Character> sortCharsByFrequency(Map<Character, Integer> frequencyMap) {
        List<Integer> listFrequencies = new ArrayList<>(frequencyMap.values());
        List<Character> listChars = new ArrayList<>(frequencyMap.keySet());
        for (int i = 0; i < listFrequencies.size(); i++) {
            for (int j = i + 1; j < listFrequencies.size(); j++) {
                if (listFrequencies.get(i) < listFrequencies.get(j)) {
                    int tempFreq = listFrequencies.get(i);
                    listFrequencies.set(i, listFrequencies.get(j));
                    listFrequencies.set(j, tempFreq);
                    char tempChar = listChars.get(i);
                    listChars.set(i, listChars.get(j));
                    listChars.set(j, tempChar);
                }
            }
        }
        return listChars;
    }
    public static String replaceCharsInText(String str, List<Character> fromChars, List<Character> toChars) {
        Map<Character, Character> map = new HashMap<>();
        int length = Math.min(fromChars.size(), toChars.size());
        for (int i = 0; i < length; i++) {
            map.put(fromChars.get(i), toChars.get(i));
        }
        StringBuilder result = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (map.containsKey(ch)) {
                result.append(map.get(ch));
            } else {
                result.append(ch);
            }
        }
        long endTime = System.nanoTime();
        finalTime = endTime - startTime;
        System.out.println("Elapsed time method \"decryptBruteForceWithRegex\": " + (finalTime) / 1_000_000.0 + " ms");
        return result.toString();
    }
    public static String replaceCharsInText(String str, char fromChar, char toChar) {
         StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (currentChar == fromChar) {
                result.append(toChar);
            } else if (currentChar == toChar) {
                result.append(fromChar);
            } else {
                result.append(currentChar);
            }
        }
        long endTime = System.nanoTime();
        finalTime = endTime - startTime;
        return result.toString();
    }
    public static long getFinalTime() {
        return finalTime;
    }
}