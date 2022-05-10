package util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SomeJUnitBasics {
    @Test
    public void supportsBasicMath() {
        assertThat(4 * 8, equalTo(null /* what really? */));
    }

    @Test
    public void appendsItemToListUsingAdd() {
        var numbers = new ArrayList<>(asList(12, 1, 1, 1, 2, 1, 3));

        numbers.add(1);

        assertThat(numbers, equalTo(null /* what really? */));
    }

    @Test
    public void doublesEachElementInAList() {
        var numbers = List.of(2, 5, 10, 105);

        var result = new ArrayList(numbers); // replace code that passes the test!

        assertThat(result, equalTo(asList(4, 10, 20, 210)));
    }

    @Test
    public void handlesInterestingFloatPointResults() {
        var result = 0.1 + 0.2;

        assertThat(result, equalTo(0.3));
        /* Replace 0.0 with a better matcher involving 0.3.
           How do we best compare against floating point numbers? */
    }

}
