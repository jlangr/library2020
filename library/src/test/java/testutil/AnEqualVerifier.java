package testutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

class AnEqualVerifier {
    @Nested
    class EqualityComparisons {
        class Alpha {
            Alpha(String a, int n) {
                this.a = a;
                this.n = n;
            }

            private final String a;
            private final int n;

            @Override
            public String toString() {
                return "Alpha{a='" + a + '\'' + ", n=" + n + '}';
            }
        }

        EqualVerifier verifier;

        @BeforeEach
        public void create() {
            verifier = new EqualVerifier();
        }

        @Nested
        class AreAllFieldsEqual {
            @Test
            void returnsTrueWhenAllFieldsAreEqual() {
                assertThat(verifier.areAllFieldsEqual(new Alpha("a", 1), new Alpha("a", 1)),
                        is(true));
            }

            @Test
            void returnsFalseWhenObj1HasNullField() {
                assertThat(verifier.areAllFieldsEqual(new Alpha(null, 1), new Alpha("a", 1)),
                        is(false));
                assertThat(verifier.errorMessages(), contains("\texpected.a <<null>> not equal actual.a <<a>>"));
            }

            @Test
            void returnsFalseWhenObj2HasNullField() {
                assertThat(verifier.areAllFieldsEqual(new Alpha("a", 1), new Alpha(null, 1)),
                        is(false));
                assertThat(verifier.errorMessages(), contains("\texpected.a <<a>> not equal actual.a <<null>>"));
            }

            @Test
            void returnsWhenBothHaveNullValue() {
                assertThat(verifier.areAllFieldsEqual(new Alpha(null, 1), new Alpha(null, 1)),
                        is(true));
            }

            @Test
            void returnsFalseWhenStringFieldNotEqual() {
                assertThat(verifier.areAllFieldsEqual(new Alpha("a", 1), new Alpha("b", 1)),
                        is(false));
                assertThat(verifier.errorMessages(), contains("\texpected.a <<a>> not equal actual.a <<b>>"));
            }

            @Test
            void returnsWhenIntFieldNotEqual() {
                assertThat(verifier.areAllFieldsEqual(new Alpha("a", 1), new Alpha("a", 2)),
                        is(false));
                assertThat(verifier.errorMessages(), contains("\texpected.n <<1>> not equal actual.n <<2>>"));
            }

            @Test
            void includesMessagesForAllFields() {
                assertThat(verifier.areAllFieldsEqual(new Alpha("a", 1), new Alpha("b", 2)),
                        is(false));
                assertThat(verifier.errorMessages(), containsInAnyOrder(
                        "\texpected.a <<a>> not equal actual.a <<b>>",
                        "\texpected.n <<1>> not equal actual.n <<2>>"));
            }
        }

        @Nested
        class AreAllFieldsEqualInList {
            @Test
            void returnsTrueWhenSoleObjectsEqual() {
                assertThat(verifier.areAllFieldsEqualInList(List.of(new Alpha("a", 1)), List.of(new Alpha("a", 1))), is(true));
                assertThat(verifier.errorMessages(), empty());
            }

            @Test
            void returnsFalseWhenStringFieldNotEqual() {
                assertThat(verifier.areAllFieldsEqualInList(List.of(new Alpha("a", 1)), List.of(new Alpha("b", 1))), is(false));
                assertThat(verifier.errorMessages(), contains(
                        "mismatch at index 0",
                        "\texpected.a <<a>> not equal actual.a <<b>>"
                ));
            }

            @Test
            void reportsOnAllMismatches() {
                verifier.areAllFieldsEqualInList(
                        List.of(new Alpha("a", 1), new Alpha("b", 0)),
                        List.of(new Alpha("b", 1), new Alpha("b", 1)));
                assertThat(verifier.errorMessages(), contains(
                        "mismatch at index 0",
                        "\texpected.a <<a>> not equal actual.a <<b>>",
                        "mismatch at index 1",
                        "\texpected.n <<0>> not equal actual.n <<1>>"
                ));
            }

            @Test
            void returnsFalseWhenListsUnequalInLength() {
                assertThat(verifier.areAllFieldsEqualInList(List.of(new Alpha("a", 1)), List.of()), is(false));
                assertThat(verifier.errorMessages(), contains("lists vary in size"));
            }
        }
    }
}
