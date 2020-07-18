package cucumber;

import com.loc.material.api.MaterialType;
import io.cucumber.java.ParameterType;
import util.Point;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

import static java.lang.Double.*;

public class ParameterTypes {
    static final String FLOAT_REGEX = "[-+]?[0-9]*\\.?[0-9]";
    static final String POINT_REGEX = "\\((" + FLOAT_REGEX + "),\\s*(" + FLOAT_REGEX + ")\\)";

    @ParameterType("\\d+/\\d+/\\d+")
    // TODO demonstrate pushing this sort of thing into production, making sure it's tested
    public LocalDate date(String date) {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(date, formatter);
    }

    @ParameterType(POINT_REGEX)
    public Point point(String xString, String yString) {
        return new Point(parseDouble(xString), parseDouble(yString));
    }

    @ParameterType("\\d+/\\d+/\\d+")
    public java.util.Date oldSchoolDate(String dateString) {
        return Date.from(date(dateString).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @ParameterType(".*")
    public MaterialType materialType(String materialType) {
        return Arrays.stream(MaterialType.values())
                .filter(type -> type.name().toLowerCase().equals(materialType.toLowerCase()))
                .findFirst().get();
    }

    @ParameterType("\\d{2}:\\d{2}")
    public LocalTime time(String time) {
        var formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(time, formatter);
    }
}
