package util;

import java.util.Arrays;
import java.util.stream.Collectors;

class NameParser {
    String baseName;
    String name;
    String[] parts;

    public NameParser(String name) {
        this.name = name;
        throwOnExcessCommas();
        baseName = baseName(name);

        parts = baseName.trim().split(" ");
    }

    private void throwOnExcessCommas() {
        if (count(name, ',') > 1)
            throw new IllegalArgumentException("name contains more than one comma");
    }

    private String baseName(String name) {
        return name.split(",")[0];
    }

    String firstName() {
        return parts[0];
    }

    String middleInitials() {
        return Arrays.stream(parts)
                .skip(1)
                .limit(parts.length - 2)
                .map(this::initial)
                .collect(Collectors.joining(" "));
    }

    String lastName() {
        return parts.length > 1 ? parts[parts.length - 1] : "";
    }

    boolean isMononym() {
        return parts.length == 1;
    }

    boolean isDuonym() {
        return parts.length == 2;
    }

    private String initial(String name) {
        return name.length() == 1 ? name : name.substring(0, 1) + ".";
    }

    public String suffix() {
        var parts = name.split(",");
        return parts.length == 1 ? "" : "," + parts[1];
    }

    // See http://stackoverflow.com/questions/275944/java-how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
    // ... if you need to convert to < Java 8
   /* private */ long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}
