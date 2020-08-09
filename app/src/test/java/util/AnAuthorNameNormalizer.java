package util;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnAuthorNameNormalizer {
    private AuthorNameNormalizer normalizer;

    @Before
    public void create() {
        normalizer = new AuthorNameNormalizer();
    }

    @Test
    public void returnsEmptyStringWhenEmpty() {
        assertThat(normalizer.normalize("")).isEqualTo("");
    }

    @Test
    public void returnsSingleWordName() {
        assertThat(normalizer.normalize("Plato")).isEqualTo("Plato");
    }

    @Test
    public void returnsLastFirstWhenFirstLastProvided() {
      assertThat(normalizer.normalize("Haruki Murakami")).isEqualTo("Murakami, Haruki");
    }

    @Ignore
    @Test
    public void trimsLeadingAndTrailingWhitespace() {
        assertThat(normalizer.normalize("  Big Boi   ")).isEqualTo("Boi, Big");
    }

    @Ignore
    @Test
    public void initializesMiddleName() {
        assertThat(normalizer.normalize("Henry David Thoreau")).isEqualTo("Thoreau, Henry D.");
    }

    @Ignore
    @Test
    public void doesNotInitializeOneLetterMiddleName() {
        assertThat(normalizer.normalize("Harry S Truman")).isEqualTo("Truman, Harry S");
    }

    @Ignore
    @Test
    public void initializesEachOfMultipleMiddleNames() {
        assertThat(normalizer.normalize("Julia Scarlett Elizabeth Louis-Dreyfus")).isEqualTo("Louis-Dreyfus, Julia S. E.");
    }

    @Ignore
    @Test
    public void appendsSuffixesToEnd() {
        assertThat(normalizer.normalize("Martin Luther King, Jr.")).isEqualTo("King, Martin L., Jr.");
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenNameContainsTwoCommas() {
        normalizer.normalize("Thurston, Howell, III");
    }
}
