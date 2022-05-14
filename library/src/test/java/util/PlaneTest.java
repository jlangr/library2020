package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaneTest {
    Plane plane;

    @BeforeEach
    public void setup() {
        plane = new Plane();
    }

    @Test
    public void multipleSimple() {
        var points = List.of(
                new Point(3, 3),
                new Point(5, -1),
                new Point(-2, 4),
                new Point(40, 5));

        assertThat(plane.kClosestPoints(points, 2),
                equalTo(List.of(new Point(3, 3), new Point(-2, 4))));
    }
}
