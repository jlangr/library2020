//package util;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//// 0. Uncomment the whole thing.
//// 1. Un-ignore the next commented-out test method.
//// 2. Run JUnit against *all* tests in the project.
//// 3. Did the current test fail? If not: You built too much code in a prior step. Undo work for prior tests and try again.
//// 4. Make sure you are clear on why the test failed.
//// 5. Write only enough code to make that failing test pass (and not break any other tests).
////    Did you write too much? Is there a simple way to get that test to pass???
//// 6. If there is a commented-out assertion, uncomment it. It should fail. If not, return to step 5.
//// 7. Return to step 1.
//
//class AnAuthorNameNormalizer {
//    private AuthorNameNormalizer normalizer;
//
//    @BeforeEach
//    void create() {
//        normalizer = new AuthorNameNormalizer();
//    }
//
//    @Disabled
//    @Test
//    void returnsEmptyStringWhenEmpty() {
//        assertThat(normalizer.normalize(""), equalTo(""));
//    }
//
//    @Disabled
//    @Test
//    void returnsSingleWordName() {
//        assertThat(normalizer.normalize("Plato"), equalTo("Plato"));
//    }
//
//    @Disabled
//    @Test
//    void returnsLastFirstWhenFirstLastProvided() {
//      assertThat(normalizer.normalize("Haruki Murakami"), equalTo("Murakami, Haruki"));
//    }
//
//    @Disabled
//    @Test
//    void trimsLeadingAndTrailingWhitespace() {
//        assertThat(normalizer.normalize("  Big Boi   "), equalTo("Boi, Big"));
//    }
//
//    @Disabled
//    @Test
//    void initializesMiddleName() {
//        assertThat(normalizer.normalize("Henry David Thoreau"), equalTo("Thoreau, Henry D."));
//    }
//
//    @Disabled
//    @Test
//    void doesNotInitializeOneLetterMiddleName() {
//        assertThat(normalizer.normalize("Harry S Truman"), equalTo("Truman, Harry S"));
//    }
//
//    @Disabled
//    @Test
//    void initializesEachOfMultipleMiddleNames() {
//        assertThat(normalizer.normalize("Julia Scarlett Elizabeth Louis-Dreyfus"), equalTo("Louis-Dreyfus, Julia S. E."));
//    }
//
//    @Disabled
//    @Test
//    void appendsSuffixesToEnd() {
//        assertThat(normalizer.normalize("Martin Luther King, Jr."), equalTo("King, Martin L., Jr."));
//    }
//
//    @Disabled
//    @Test
//    void throwsWhenNameContainsTwoCommas() {
//        var thrown = assertThrows(InvalidNameException.class, () ->
//            normalizer.normalize("Thurston, Howell, III"));
//        assertThat(thrown.getMessage(), equalTo("name contains 2 commas; maximum is one comma"));
//    }
//}
