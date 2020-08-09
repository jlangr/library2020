package util;

public class AuthorNameNormalizer {

    private String[] parts;

    public String normalize(String fullName) {
        parts = fullName.split(" ");
        if (isMononym())
            return formatMononym();
        return formatDuonym();
    }

    private String formatMononym() {
        return first();
    }

    private String formatDuonym() {
        return last() + ", " + first();
    }

    private String last() {
        return parts[1];
    }

    private String first() {
        return parts[0];
    }

    private boolean isMononym() {
        return parts.length == 1;
    }

    // See http://stackoverflow.com/questions/275944/java-how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
    // ... if you need to convert to < Java 8
//   long count(String string, char c) {
//        return string.chars().filter(ch -> ch == c).count();
//    }
}

