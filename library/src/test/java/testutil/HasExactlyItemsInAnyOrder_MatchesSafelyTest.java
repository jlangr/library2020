package testutil;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

class HasExactlyItemsInAnyOrder_MatchesSafelyTest {
    @Test
    void falseWhenArgIsNull() {
        assertThat(createMatcher(singletonList("a")).matchesSafely(null), is(false));
    }

    @Test
    void falseWhenReceiverListIsNull() {
        assertThat(createMatcher(null).matchesSafely(singletonList("a")), is(false));
    }

    @Test
    void trueWhenBothAreNull() {
        assertThat(createMatcher(null).matchesSafely(null), is(true));
    }

    @Test
    void falseWhenSizesDiffer() {
        assertThat(createMatcher(singletonList("a")).matchesSafely(asList("a", "b")), is(false));
    }

    @Test
    void trueWhenBothContainSingleSameItem() {
        assertThat(createMatcher(singletonList("a")).matchesSafely(singletonList("a")), is(true));
    }

    @Test
    void trueWhenBothContainSameItemsInSameOrder() {
        assertThat(createMatcher(asList("a", "b")).matchesSafely(asList("a", "b")), is(true));
    }

    @Test
    void trueWhenBothContainSameItemsDifferentOrder() {
        assertThat(createMatcher(asList("a", "b")).matchesSafely(asList("b", "a")), is(true));
    }

    @Test
    void falseWhenBothHaveDifferingDuplicatesButSetsMatch() {
        assertThat(createMatcher(asList("a", "b", "b")).matchesSafely(asList("b", "a", "a")), is(false));
    }

    @Test
    void falseWhenOneIsSubsetOfOther() {
        assertThat(createMatcher(asList("a", "b", "b")).matchesSafely(asList("b", "a", "c")), is(false));
    }

    @Test
    void falseWhenItemsDontMatch() {
        assertThat(createMatcher(asList("a", "b", "c")).matchesSafely(asList("a", "b", "d")), is(false));
    }

    private HasExactlyItemsInAnyOrder<String> createMatcher(List<String> arg) {
        return new HasExactlyItemsInAnyOrder<>(arg);
    }
}
