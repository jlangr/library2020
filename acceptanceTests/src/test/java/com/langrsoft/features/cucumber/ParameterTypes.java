package com.langrsoft.features.cucumber;

import com.langrsoft.external.MaterialType;
import io.cucumber.java.ParameterType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class ParameterTypes {

    @ParameterType("\\d+/\\d+/\\d+")
    // TODO demonstrate pushing this sort of thing into production, making sure it's tested
    public LocalDate date(String date) {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(date, formatter);
    }

    @ParameterType("\\d+/\\d+/\\d+")
    public java.util.Date oldSchoolDate(String dateString) {
        return Date.from(date(dateString).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // TODO let coders build this for exercise--delete from here
    // They should do a simple match on any string,
    // then convert it to a MaterialType. Note that
    // the case of the materialtype could be anything.
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
