package com.langrsoft.util;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

class DateUtilTest {
    static final Date NEW_YEARS_DAY = DateUtil.create(2011, JANUARY, 1);

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
        MatcherAssert.assertThat(DateUtil.addDays(DateUtil.create(2017, MARCH, 1), 21), equalTo(DateUtil.create(2017, MARCH, 22)));
        MatcherAssert.assertThat(DateUtil.addDays(NEW_YEARS_DAY, 367), equalTo(DateUtil.create(2012, JANUARY, 3)));
        MatcherAssert.assertThat(DateUtil.addDays(DateUtil.create(2017, DECEMBER, 31), 32), equalTo(DateUtil.create(2018, FEBRUARY, 1)));
    }

    @Test
    void answersDaysFromWithinSameYear() {
        var laterBy15 = DateUtil.addDays(NEW_YEARS_DAY, 15);

        MatcherAssert.assertThat(DateUtil.daysFrom(NEW_YEARS_DAY, laterBy15), equalTo(15));
    }

    @Test
    void answersDaysFromToNextYear() {
        var laterBy375 = DateUtil.addDays(NEW_YEARS_DAY, 375);

        MatcherAssert.assertThat(DateUtil.daysFrom(NEW_YEARS_DAY, laterBy375), equalTo(375));
    }

    @Test
    void answersDaysFromManyYearsOut() {
        var later = DateUtil.addDays(NEW_YEARS_DAY, 2100);

        MatcherAssert.assertThat(DateUtil.daysFrom(NEW_YEARS_DAY, later), equalTo(2100));
    }

    @Test
    void convertsJavaUtilDateToLocalDate() {
        var converted = DateUtil.toLocalDate(DateUtil.create(2016, MAY, 15));

        assertThat(converted.getDayOfMonth(), equalTo(15));
        assertThat(converted.getYear(), equalTo(2016));
        assertThat(converted.getMonth(), equalTo(Month.MAY));
    }

    @Test
    void getCurrentDateReturnsInjectedValue() {
        DateUtil.fixClockAt(NEW_YEARS_DAY);

        var date = DateUtil.getCurrentDate();

        assertThat(date, equalTo(NEW_YEARS_DAY));
    }

    @Test
    void getCurrentLocalDateReturnsInjectedValue() {
        DateUtil.fixClockAt(NEW_YEARS_DAY);

        var date = DateUtil.getCurrentLocalDate();

        MatcherAssert.assertThat(date, equalTo(DateUtil.toLocalDate(NEW_YEARS_DAY)));
    }

    @Test
    void ageInYearsDeterminesYearsBetweenTwoLocalDates() {
        var age = DateUtil.ageInYears(LocalDate.of(2010, Month.MAY, 1), LocalDate.of(2015, Month.MAY, 2));

        assertThat(age, equalTo(5));
    }

    @Nested
    class DaysAfter {
        Date janOne2025 = DateUtil.create(2025, JANUARY, 1);

        @Test
        void isZeroWhenSecondDayNotAfterFirst() {
            assertThat(DateUtil.daysAfter(janOne2025, DateUtil.create(2025, JANUARY, 1)), equalTo(0));
        }

        @Test
        void whenDayWithinSameYear() {
            assertThat(DateUtil.daysAfter(janOne2025, DateUtil.create(2025, JANUARY, 31)), equalTo(30));
        }

        @Test
        void whenInSubsequentYear() {
            assertThat(DateUtil.daysAfter(janOne2025, DateUtil.create(2026, JANUARY, 1)), equalTo(365));
        }

        @Test
        void whenYearsFromFirstDay() {
            assertThat(DateUtil.daysAfter(janOne2025, DateUtil.create(2028, JANUARY, 1)), equalTo(1095));
        }

        @Test
        void accountsForLeapDay() {
            assertThat(DateUtil.daysAfter(DateUtil.create(2024, JANUARY, 1),
                    DateUtil.create(2025, JANUARY, 1)), equalTo(366));
        }
    }

    @Nested
    class MockUse {
        @Test
        void fixedClock() {
            var ides2025 = DateUtil.create(2025, MARCH, 17);

            DateUtil.fixClockAt(ides2025);

            assertThat(DateUtil.getCurrentDate(), equalTo(ides2025));
        }

        @Test
        void resetClock() {
            var ides2025 = DateUtil.create(2025, MARCH, 17);
            DateUtil.fixClockAt(ides2025);

            DateUtil.resetClock();

            assertThat(DateUtil.getCurrentDate(), not(equalTo(ides2025)));
        }
    }
}
