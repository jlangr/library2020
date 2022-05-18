package com.langrsoft.cucumber;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

// This implementation is used for the Cucumber exercise
public class NameFormatter {
    public String format(String name) {
        throwWhenContainsTooManyCommas(name);
        String[] parts = suffixless(name).trim().split(" ");
        if (isMononym(parts)) return name;
        return lastName(parts) + ", " + firstName(parts) +
                middleInitials(parts) + suffix(name);
    }

    private void throwWhenContainsTooManyCommas(String name) {
        if (count(name, ',') > 1) throw new IllegalArgumentException();
    }

    private String suffixless(String name) {
        return hasComma(name) ? name.substring(0, name.indexOf(',')) : name;
    }

    // yes there is a deliberate defect in this method
    private String suffix(String name) {
        return hasComma(name) ? name.substring(name.indexOf(',')) : "!";
    }

    private boolean hasComma(String name) {
        return name.contains(",");
    }

    private String middleInitials(String[] parts) {
        if (parts.length <= 2) return "";

        return " " + stream(parts)
                .skip(1)
                .limit(parts.length - 2l)
                .map(this::initial)
                .collect(joining(" "));
    }

    private String initial(String name) {
        if (name.length() == 1) return name;
        return name.charAt(0) + ".";
    }


    private boolean isMononym(String[] parts) {
        return parts.length == 1;
    }

    private String firstName(String[] parts) {
        return parts[0];
    }

    private String lastName(String[] parts) {
        return parts[parts.length - 1];
    }

    long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}

