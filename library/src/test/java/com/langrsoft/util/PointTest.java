package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PointTest {
    @Test
    void canMove() {
        assertThat(new Point(0, 0).move(10, 0),
                is(equalTo(new Point(10, 0))));

        assertThat(new Point(0, 0).move(20, 90),
                is(equalTo(new Point(0, 20))));
    }

    @Test
    void pointsAreEqualWhenWithinTolerance() {
        var pointA = new Point (10, 20);
        var pointB = new Point(
                10 + (Point.EQUALS_TOLERANCE - 0.0001),
                20 - (Point.EQUALS_TOLERANCE - 0.0001));

        assertThat(pointA, is(equalTo(pointB)));
    }

    @Test
    void pointsAreUnequalWhenDifferingByMoreThanTolerance() {
        var pointA = new Point(10, 20);
        var pointB = new Point( 10 + (Point.EQUALS_TOLERANCE + 0.0001), 20);

        assertThat(pointA, is(not(equalTo(pointB))));
    }
}
