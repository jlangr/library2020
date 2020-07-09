package cucumber;

import io.cucumber.cucumberexpressions.Transformer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateMapper implements Transformer<LocalDate> {
    // TODO: old school, no longer used
    @Override
    public LocalDate transform(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
