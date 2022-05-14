package util.matchers;

import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.matchers.LessThan.lessThan;

public class LessThanTest {
    @Test
    public void passesWhenLessThan() {
        assertThat(5, lessThan(6));
    }

    @Test
    public void notPassesWhenEqualTo() {
        assertThat(6, not(lessThan(6)));
    }

    @Test
    public void notPassesWhenGreaterThan() {
        assertThat(7, not(lessThan(6)));
    }

    @Test
    public void passesWithDoubles() {
        assertThat(5.0, lessThan(6.0));
    }

    @Test
    public void failureMessageIsUseful() {
        var thrown = assertThrows(AssertionError.class, () ->
                assertThat(6, lessThan(4)));
        assertThat(thrown.getMessage(), containsString("A number less than 4"));
    }
}
