package com.langrsoft.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static testutil.HasExactlyItemsInAnyOrder.hasExactlyItemsInAnyOrder;

class MultiMapTest {
    private MultiMap<Object, Object> map;

    @BeforeEach
    void initialize() {
        map = new MultiMap<>();
    }

    @Test
    void isEmptyOnCreation() {
        assertThat(map.size(), equalTo(0));
        assertThat(map.valuesSize(), equalTo(0));
    }

    @Test
    void returnsValuesAssociatedWithKeyAsList() {
        map.put("a", "alpha");

        var values = map.get("a");

        assertThat(values, hasExactlyItemsInAnyOrder("alpha"));
    }

    @Test
    void incrementsSizeForMultipleKeys() {
        map.put("a", "");
        map.put("b", "");

        assertThat(map.size(), equalTo(2));
    }

    @Test
    void allowsMultipleElementsSameKey() {
        map.put("a", "alpha1");
        map.put("a", "alpha2");

        var values = map.get("a");

        assertThat(values, hasExactlyItemsInAnyOrder("alpha2", "alpha1"));
    }

    @Test
    void valuesSizeRepresentsTotalCountOfValues() {
        map.put("a", "alpha");
        map.put("b", "beta1");
        map.put("b", "beta2");

        assertThat(map.valuesSize(), equalTo(3));
    }

    @Test
    void returnsOnlyValuesAssociatedWithKey() {
        map.put("a", "alpha");
        map.put("b", "beta");

        var values = map.get("b");

        assertThat(values, hasExactlyItemsInAnyOrder("beta"));
    }

    @Test
    void throwsOnPutOfNullKey() {
        assertThrows(NullPointerException.class, () ->
                map.put(null, ""));
    }

    @Test
    void clearEliminatesAllData() {
        map.put("a", "");
        map.put("b", "");

        map.clear();

        assertThat(map.size(), equalTo(0));
        assertThat(map.valuesSize(), equalTo(0));
    }

    @Test
    void returnsCombinedCollectionOfAllValues() {
        map.put("a", "alpha");
        map.put("b", "beta");

        var values = map.values();

        assertThat(values, hasExactlyItemsInAnyOrder("alpha", "beta"));
    }
}
