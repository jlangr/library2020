package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ListUtilTest {
    @Test
    void mapTransformsUsingGetter() {
        var s = asList("1", "22", "333");

        var mappedToLengths = new ListUtil().map(s, "length", String.class, Integer.class);

        assertThat(mappedToLengths, equalTo(asList(1, 2, 3)));
    }
}
