package com.javarush.caesarcipher.lapkin.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.javarush.caesarcipher.lapkin.constans.Alphabet.*;

public class TextHandler {


    private static long finalTime;

    public static String encryptByKey(String text, int keyShift) {
        return shiftText(text, keyShift);
    }
    public static String decryptByKey(String text, int keyShift) {
        return shiftText(text, -keyShift);
    }
    public static String decryptBruteForceWithRegex(String text, String regex) {
        long startTime = System.nanoTime();  // Initial measurement time method "decryptWithRegex"
        int maxKeyRange = CYRILLIC_RANGE/2;
        Pattern pattern = Pattern.compile(regex);
        String bestMatch = null;
        int highestMatchLength = 0;
        int bestKeyShift = 0;
        for (int keyShift = 0; keyShift < maxKeyRange; keyShift++) {
            String decryptedText = decryptByKey(text, keyShift);
            Matcher matcher = pattern.matcher(decryptedText);
            while (matcher.find()) {
                String match = matcher.group();
                if (match.length() > highestMatchLength) {
                    highestMatchLength = match.length();
                    bestMatch = decryptedText;
                    bestKeyShift = keyShift;
                }
            }
        }
        System.out.println("Decryption key = " + bestKeyShift);
        long endTime = System.nanoTime();  // Final measurement time method "decryptWithRegex"
        finalTime = endTime - startTime;
        System.out.println("Elapsed time method \"decryptBruteForceWithRegex\": " + (finalTime) / 1_000_000.0 + " ms");
        return bestMatch != null ? bestMatch : "No match found";
    }
    public static long getFinalTime() {
        return finalTime;
    }
    private static String shiftText(String text, int keyShift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if ((character >= ASCII_START && character <= ASCII_END) ||
                    (character >= CYRILLIC_START && character <= CYRILLIC_END) ||
                    character == CYRILLIC_YO_UP_CASE || character == CYRILLIC_YO_LOW_CASE) {
                int base = (character >= CYRILLIC_START) ? CYRILLIC_START : ASCII_START;
                int range = (character >= CYRILLIC_START) ? CYRILLIC_RANGE : ASCII_RANGE;
                int offset = character - base;
                char newChar = (char) (((offset + keyShift) % range + range) % range + base);
                result.append(newChar);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}
