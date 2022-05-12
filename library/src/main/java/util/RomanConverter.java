package util;

import java.util.List;

public class RomanConverter {
    class Conversion {
        private final int arabicDigit;
        private final String romanDigit;

        Conversion(int arabicDigit, String romanDigit) {
            this.arabicDigit = arabicDigit;
            this.romanDigit = romanDigit;
        }
    }
    List<Conversion> conversions = List.of(
            new Conversion(100, "C"),
            new Conversion(10, "X"),
            new Conversion(1, "I")
    );
    public String convert(int arabic) {
        var roman = "";
        for (var conversion: conversions) {
            var digitsNeeded = arabic / conversion.arabicDigit;
            roman += conversion.romanDigit.repeat(digitsNeeded);
            arabic -= digitsNeeded * conversion.arabicDigit;
        }
        return roman;
    }
}
