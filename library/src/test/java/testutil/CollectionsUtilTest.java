package testutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionsUtilTest {
    private Collection<Object> collection;

    @BeforeEach
    void initialize() {
        collection = new ArrayList<>();
    }

    @Test
    void soleElementRetrievesFirstAndOnlyElement() {
        collection.add("a");

        var soleElement = CollectionsUtil.soleElement(collection);

        assertThat(soleElement, equalTo("a"));
    }

    @Test
    void soleElementThrowsWhenNoElementsExist() {
        var thrown = assertThrows(AssertionError.class, () ->
                CollectionsUtil.soleElement(collection));

        assertThat(thrown.getMessage(), equalTo(CollectionsUtil.NO_ELEMENTS));
    }

    @Test
    void soleElementThrowsWhenMoreThanOneElement() {
        collection.add("a");
        collection.add("b");

        var thrown = assertThrows(AssertionError.class, () ->
                CollectionsUtil.soleElement(collection));
        assertThat(thrown.getMessage(), equalTo(CollectionsUtil.MORE_THAN_ONE_ELEMENT));
    }
}
