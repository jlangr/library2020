package util;

import java.util.Objects;

import static java.lang.Math.*;

public class Point {
    double x;
    double y;
    public static final double EQUALS_TOLERANCE = 0.0005;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        var xWithinTolerance = abs(point.x - x) <= EQUALS_TOLERANCE;
        var yWithinTolerance = abs(point.y - y) <= EQUALS_TOLERANCE;
        return xWithinTolerance && yWithinTolerance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Point move(int distance, int degrees) {
        double radians = toRadians(degrees);
        double x = distance * cos(radians) + this.x;
        double y = distance * sin(radians) + this.y;
        return new Point(x, y);
    }
}
