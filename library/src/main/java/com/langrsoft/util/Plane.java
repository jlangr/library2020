package com.langrsoft.util;

import java.util.*;

import static java.lang.Math.*;

public class Plane {
    public List<Point> kClosestPoints(List<Point> points, int k) {
        var map = new HashMap<Double, List<Integer>>();
        var queue = new PriorityQueue<Double>();
        for (int i = 0; i < points.size(); i++) {
            var point = points.get(i);
            var distance = sqrt(square(point.y - 0) + square(point.x - 0));
            listAt(map, distance).add(i);
            queue.add(distance);
        }

        var results = new ArrayList<Point>();
        while (!queue.isEmpty() && results.size() < k)
            for (int j : map.get(queue.poll()))
                results.add(points.get(j));
        return results;
    }

    private double square(double n) {
        return n * n;
    }

    private List<Integer> listAt(HashMap<Double, List<Integer>> map, double distance) {
        return map.compute(distance, (key, value) -> value == null ? new ArrayList<>() : value);
    }
}
