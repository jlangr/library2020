package util.matchers;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.matchers.LessThan.lessThan;

class LessThanTest {
    @Test
    void passesWhenLessThan() {
        assertThat(5, lessThan(6));
    }

    @Test
    void notPassesWhenEqualTo() {
        assertThat(6, not(lessThan(6)));
    }

    @Test
    void notPassesWhenGreaterThan() {
        assertThat(7, not(lessThan(6)));
    }

    @Test
    void passesWithDoubles() {
        assertThat(5.0, lessThan(6.0));
    }

    @Test
    void failureMessageIsUseful() {
        var thrown = assertThrows(AssertionError.class, () ->
                assertThat(6, lessThan(4)));
        assertThat(thrown.getMessage(), containsString("A number less than 4"));
    }
}
