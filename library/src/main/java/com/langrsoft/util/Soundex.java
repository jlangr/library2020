package com.langrsoft.util;

import java.util.HashMap;
import java.util.Map;

public class Soundex {

    Map<Character, Character> digits = new HashMap<>();

    public Soundex() {
        putAll("bfpv", '1');
        putAll("cgjkqsxz", '2');
        putAll("dt", '3');
        putAll("l", '4');
        putAll("mn", '5');
        putAll("r", '6');
    }

    public String encode(String string) {
        return string;
    }

    private void putAll(String letters, char digit) {
        for (int i = 0; i < letters.length(); i++)
            digits.put(letters.charAt(i), digit);
    }

    boolean isVowel(char letter) {
        return "aeiouy".indexOf(letter) != -1;
    }

    boolean isVowelLike(char letter) {
        return "aeiouywh".indexOf(letter) != -1;
    }
}
