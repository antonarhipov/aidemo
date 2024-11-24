package me.arhan.aidemo.custom;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.arhan.aidemo.custom.Orientation.COUNTERCLOCKWISE;
import static me.arhan.aidemo.custom.Orientation.getOrientation;

public class JarvisMarch implements Algorithm {

    public @NotNull List<Coordinate> run(@NotNull List<Coordinate> points) {
        if (points.size() < MINIMUM_POINTS_FOR_HULL) return Collections.emptyList();

        List<Coordinate> convexHull = new ArrayList<>();
        int leftmostPointIndex = findLeftmostPointIndex(points);
        int currentPointIndex = leftmostPointIndex;

        do {
            convexHull.add(points.get(currentPointIndex));
            currentPointIndex = findNextHullPoint(points, currentPointIndex);
        } while (currentPointIndex != leftmostPointIndex);

        printConvexHull(convexHull);
        return convexHull;
    }

    private int findLeftmostPointIndex(List<Coordinate> points) {
        int leftmostIndex = 0;
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getX() < points.get(leftmostIndex).getX()) {
                leftmostIndex = i;
            }
        }
        return leftmostIndex;
    }

    private int findNextHullPoint(List<Coordinate> points, int currentPointIndex) {
        int nextPointIndex = (currentPointIndex + 1) % points.size();
        for (int i = 0; i < points.size(); i++) {
            if (getOrientation(points.get(currentPointIndex), points.get(i), points.get(nextPointIndex)) == COUNTERCLOCKWISE) {
                nextPointIndex = i;
            }
        }
        return nextPointIndex;
    }

    private void printConvexHull(List<Coordinate> convexHull) {
        convexHull.stream().map(point -> "(" + point.getX() + ", " + point.getY() + ")").forEach(System.out::println);
    }
}