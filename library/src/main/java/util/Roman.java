package util;

import java.util.List;

public class Roman {
    class Conversion {
        final Integer arabicDigit;
        final String romanDigit;
        Conversion(Integer arabicDigit, String romanDigit) {
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
        var result = "";
        for (var conversion: conversions) {
            var numDigits = arabic / conversion.arabicDigit;
            result += conversion.romanDigit.repeat(numDigits);
            arabic -= numDigits * conversion.arabicDigit;
        }
        return result;
    }
}
