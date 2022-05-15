package com.langrsoft.util.matchers;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LessThanTest {
    @Test
    void passesWhenLessThan() {
        MatcherAssert.assertThat(5, LessThan.lessThan(6));
    }

    @Test
    void notPassesWhenEqualTo() {
        MatcherAssert.assertThat(6, CoreMatchers.not(LessThan.lessThan(6)));
    }

    @Test
    void notPassesWhenGreaterThan() {
        MatcherAssert.assertThat(7, CoreMatchers.not(LessThan.lessThan(6)));
    }

    @Test
    void passesWithDoubles() {
        MatcherAssert.assertThat(5.0, LessThan.lessThan(6.0));
    }

    @Test
    void failureMessageIsUseful() {
        var thrown = assertThrows(AssertionError.class, () ->
                MatcherAssert.assertThat(6, LessThan.lessThan(4)));
        assertThat(thrown.getMessage(), containsString("A number less than 4"));
    }
}
