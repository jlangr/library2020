package util;

public class AuthorNameNormalizer {

    private NameParser parser;

    public String normalize(String name) {
        parse(name);
        if (parser.isMononym())
            return name;
        if (parser.isDuonym())
            return formatDuonym();

        return formatWesternName();
    }

    private void parse(String name) {
        parser = new NameParser(name);
    }

    private String formatDuonym() {
        return String.format("%s, %s", parser.lastName(), parser.firstName());
    }

    private String formatWesternName() {
        return String.format("%s, %s %s%s", parser.lastName(), parser.firstName(), parser.middleInitials(), parser.suffix());
    }
}

