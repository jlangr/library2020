package testutil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionsUtilTest {
    private Collection<Object> collection;

    @BeforeEach
    public void initialize() {
        collection = new ArrayList<>();
    }

    @Test
    public void soleElementRetrievesFirstAndOnlyElement() {
        collection.add("a");

        var soleElement = CollectionsUtil.soleElement(collection);

        assertThat(soleElement, equalTo("a"));
    }

    // TODO think there's a replacement for this in Java now
    @Test
    public void soleElementThrowsWhenNoElementsExist() {
        var thrown = assertThrows(AssertionError.class, () ->
                CollectionsUtil.soleElement(collection));

        assertThat(thrown.getMessage(), equalTo(CollectionsUtil.NO_ELEMENTS));
    }

    @Test
    public void soleElementThrowsWhenMoreThanOneElement() {
        collection.add("a");
        collection.add("b");

        var thrown = assertThrows(AssertionError.class, () ->
                CollectionsUtil.soleElement(collection));
        assertThat(thrown.getMessage(), equalTo(CollectionsUtil.MORE_THAN_ONE_ELEMENT));
    }
}
