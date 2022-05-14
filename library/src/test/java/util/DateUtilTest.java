package util;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static util.DateUtil.*;

class DateUtilTest {
    static final Date NEW_YEARS_DAY = create(2011, JANUARY, 1);

    @Test
    void createGeneratedProperDateElements() {
        var calendar = Calendar.getInstance();

        calendar.setTime(NEW_YEARS_DAY);

        assertThat(calendar.get(YEAR), equalTo(2011));
        assertThat(calendar.get(MONTH), equalTo(JANUARY));
        assertThat(calendar.get(DAY_OF_MONTH), equalTo(1));
        assertThat(calendar.get(HOUR_OF_DAY), equalTo(0));
        assertThat(calendar.get(MINUTE), equalTo(0));
        assertThat(calendar.get(SECOND), equalTo(0));
        assertThat(calendar.get(MILLISECOND), equalTo(0));
    }

    @Test
    void addDaysAnswersLaterDate() {
        assertThat(addDays(create(2017, MARCH, 1), 21), equalTo(create(2017, MARCH, 22)));
        assertThat(addDays(NEW_YEARS_DAY, 367), equalTo(create(2012, JANUARY, 3)));
        assertThat(addDays(create(2017, DECEMBER, 31), 32), equalTo(create(2018, FEBRUARY, 1)));
    }

    @Test
    void answersDaysFromWithinSameYear() {
        var laterBy15 = addDays(NEW_YEARS_DAY, 15);

        assertThat(daysFrom(NEW_YEARS_DAY, laterBy15), equalTo(15));
    }

    @Test
    void answersDaysFromToNextYear() {
        var laterBy375 = addDays(NEW_YEARS_DAY, 375);

        assertThat(daysFrom(NEW_YEARS_DAY, laterBy375), equalTo(375));
    }

    @Test
    void answersDaysFromManyYearsOut() {
        var later = addDays(NEW_YEARS_DAY, 2100);

        assertThat(daysFrom(NEW_YEARS_DAY, later), equalTo(2100));
    }

    @Test
    void convertsJavaUtilDateToLocalDate() {
        var converted = toLocalDate(create(2016, MAY, 15));

        assertThat(converted.getDayOfMonth(), equalTo(15));
        assertThat(converted.getYear(), equalTo(2016));
        assertThat(converted.getMonth(), equalTo(Month.MAY));
    }

    @Test
    void getCurrentDateReturnsInjectedValue() {
        fixClockAt(NEW_YEARS_DAY);

        var date = getCurrentDate();

        assertThat(date, equalTo(NEW_YEARS_DAY));
    }

    @Test
    void getCurrentLocalDateReturnsInjectedValue() {
        fixClockAt(NEW_YEARS_DAY);

        var date = getCurrentLocalDate();

        assertThat(date, equalTo(toLocalDate(NEW_YEARS_DAY)));
    }

    @Test
    void ageInYearsDeterminesYearsBetweenTwoLocalDates() {
        var age = ageInYears(LocalDate.of(2010, Month.MAY, 1), LocalDate.of(2015, Month.MAY, 2));

        assertThat(age, equalTo(5));
    }
}
