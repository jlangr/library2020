package com.langrsoft.util;

import java.util.Objects;

import static java.lang.Math.*;

public class Point {
    final double x;
    final double y;
    public static final double EQUALS_TOLERANCE = 0.0005;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var point = (Point) o;
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

    public Point move(double distance, double degrees) {
        var radians = toRadians(degrees);
        var newX = distance * cos(radians) + this.x;
        var newY = distance * sin(radians) + this.y;
        return new Point(newX, newY);
    }
}
