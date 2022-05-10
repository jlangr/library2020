package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

// WARNING: Do not try to mix JUnit 4 elements (e.g. @org.junit.Test or
// @org.junit.Before) with their JUnit 5 counterparts. It's easy to do,
// given the way IDEA tries to automatically help you out with the imports.
//
// If something fishy is going on, it's usually mixed imports.
//
// In summary: Use either JUnit 4.x or JUnit 5.x elements in a test, never both.

// Note that the test classes and methods should be package-level, not public,
// when using JUnit 5. You will see a warning if you make them public.

class AFailingJUnit5TestClass_YOU_CAN_DELETE {
    String something;

    @BeforeEach
    void should_employ_BeforeEach_instead_of_Before() {
        something = "something else";
    }

    @Test
    void works_just_fine_with_the_proper_imports_and_gradle_config() {
        assertThat(something, equalTo("something"));
    }
}
