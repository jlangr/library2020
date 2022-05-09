package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnAuthorNameNormalizerTest {
    private AuthorNameNormalizer normalizer;

    @BeforeEach
    void create() {
        normalizer = new AuthorNameNormalizer();
    }

    @Test
    void returnsEmptyStringWhenEmpty() {
        assertThat(normalizer.normalize("")).isEmpty();
    }

    @Test
    public void returnsSingleWordName() {
        assertThat(normalizer.normalize("Plato")).isEqualTo("Plato");
    }

    @Test
    public void returnsLastFirstWhenFirstLastProvided() {
      assertThat(normalizer.normalize("Haruki Murakami")).isEqualTo("Murakami, Haruki");
    }

    @Test
    public void trimsLeadingAndTrailingWhitespace() {
        assertThat(normalizer.normalize("  Big Boi   ")).isEqualTo("Boi, Big");
    }

    @Test
    public void initializesMiddleName() {
        assertThat(normalizer.normalize("Henry David Thoreau")).isEqualTo("Thoreau, Henry D.");
    }

    @Test
    public void doesNotInitializeOneLetterMiddleName() {
        assertThat(normalizer.normalize("Harry S Truman")).isEqualTo("Truman, Harry S");
    }

    @Test
    public void initializesEachOfMultipleMiddleNames() {
        assertThat(normalizer.normalize("Julia Scarlett Elizabeth Louis-Dreyfus")).isEqualTo("Louis-Dreyfus, Julia S. E.");
    }

    @Test
    public void appendsSuffixesToEnd() {
        assertThat(normalizer.normalize("Martin Luther King, Jr.")).isEqualTo("King, Martin L., Jr.");
    }

    @Test()
//    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenNameContainsTwoCommas() {
        assertThatThrownBy(() -> {
            normalizer.normalize("Thurston, Howell, III");
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name contains more than one comma");
    }
}
