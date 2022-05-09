package util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AnAuthorNameNormalizer {
    private AuthorNameNormalizer normalizer;

    @Before
    public void create() {
        normalizer = new AuthorNameNormalizer();
    }

    @Test
    public void returnsEmptyStringWhenEmpty() {
        assertThat(normalizer.normalize(""), equalTo(""));
    }

    @Test
    public void returnsSingleWordName() {
        assertThat(normalizer.normalize("Plato"), equalTo("Plato"));
    }

    @Test
    public void returnsLastFirstWhenFirstLastProvided() {
      assertThat(normalizer.normalize("Haruki Murakami"), equalTo("Murakami, Haruki"));
    }

    @Test
    public void trimsLeadingAndTrailingWhitespace() {
        assertThat(normalizer.normalize("  Big Boi   "), equalTo("Boi, Big"));
    }

    @Test
    public void initializesMiddleName() {
        assertThat(normalizer.normalize("Henry David Thoreau"), equalTo("Thoreau, Henry D."));
    }

    @Test
    public void doesNotInitializeOneLetterMiddleName() {
        assertThat(normalizer.normalize("Harry S Truman"), equalTo("Truman, Harry S"));
    }

    @Test
    public void initializesEachOfMultipleMiddleNames() {
        assertThat(normalizer.normalize("Julia Scarlett Elizabeth Louis-Dreyfus"), equalTo("Louis-Dreyfus, Julia S. E."));
    }

    @Test
    public void appendsSuffixesToEnd() {
        assertThat(normalizer.normalize("Martin Luther King, Jr."), equalTo("King, Martin L., Jr."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenNameContainsTwoCommas() {
        normalizer.normalize("Thurston, Howell, III");
    }
}
