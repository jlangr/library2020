package util;


import java.util.Arrays;
import java.util.stream.Collectors;

public class AuthorNameNormalizer {
    private String[] parts;
    private String trimmedFullName;
    private String baseName;

    public String normalize(String fullName) {
        validate(fullName);
        parse(fullName);

        if (isMononym()) return formatMononym();
        if (isDuonym())  return formatDuonym();
        return formatWesternStandardName();
    }

    private void validate(String fullName) {
        if (hasTooManyCommas(fullName))
            throw new IllegalArgumentException();
    }

    private boolean hasTooManyCommas(String fullName) {
        return count(fullName, ',') > 1;
    }

    private void parse(String fullName) {
        trimmedFullName = fullName.trim();
        baseName = removeSuffix(trimmedFullName);
        parts = baseName.split(" ");
    }

    private String suffix(String fullName) {
        var nameAndSuffix = fullName.split(",");
        return nameAndSuffix.length == 1
                ? ""
                : String.format(",%s", nameAndSuffix[1]);

    }

    private String removeSuffix(String name) {
        return name.split(",")[0];
    }

    private String middleInitials() {
        return Arrays.stream(parts)
                .skip(1)
                .limit(parts.length - 2)
                .map(this::initial)
                .collect(Collectors.joining(" "));
    }

    private String initial(String name) {
        return name.length() == 1
                ? name
                : String.format("%s.", name.substring(0, 1));
    }

    private boolean isDuonym() {
        return parts.length == 2;
    }

    private String lastName() {
        return parts[parts.length - 1];
    }

    private String firstName() {
        return parts[0];
    }

    private boolean isMononym() {
        return !trimmedFullName.contains(" ");
    }

    private String formatMononym() {
        return baseName;
    }

    private String formatDuonym() {
        return String.format("%s, %s", lastName(), firstName());
    }

    private String formatWesternStandardName() {
        return String.format("%s, %s %s%s", lastName(), firstName(), middleInitials(), suffix(trimmedFullName));
    }

    long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}

