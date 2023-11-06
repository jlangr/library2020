package util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

class APalindromeTester {
    @Test
    void isOrNot() {
        assertThat(PalindromeTester.isPalindrome("a"), is(true));

    }
}
