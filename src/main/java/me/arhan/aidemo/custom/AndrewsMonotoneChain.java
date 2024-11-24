package me.arhan.aidemo.custom;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.arhan.aidemo.custom.Orientation.COUNTERCLOCKWISE;
import static me.arhan.aidemo.custom.Orientation.getOrientation;

public class AndrewsMonotoneChain implements Algorithm {

    public @NotNull List<Coordinate> run(@NotNull List<Coordinate> dts) {
        if (dts.size() < MINIMUM_POINTS_FOR_HULL) return Collections.emptyList();

        // Sort points lexicographically
        dts.sort((a, b) -> a.x == b.x ? Integer.compare(a.y, b.y) : Integer.compare(a.x, b.x));

        List<Coordinate> hull = new ArrayList<>(dts.size() * 2);

        // Build lower hull
        for (Coordinate point : dts) {
            while (hull.size() >= 2 && getOrientation(hull.get(hull.size() - 2), hull.get(hull.size() - 1), point) != COUNTERCLOCKWISE) {
                hull.remove(hull.size() - 1);
            }
            hull.add(point);
        }

        // Build upper hull
        int lowerHullSize = hull.size();
        for (int i = dts.size() - 2; i >= 0; i--) {
            Coordinate point = dts.get(i);
            while (hull.size() > lowerHullSize && getOrientation(hull.get(hull.size() - 2), hull.get(hull.size() - 1), point) != COUNTERCLOCKWISE) {
                hull.remove(hull.size() - 1);
            }
            hull.add(point);
        }

        // Remove the last point as it's the same as the first point
        hull.remove(hull.size() - 1);

        // Print the points in the convex hull
        for (Coordinate temp : hull) {
            System.out.println("(" + temp.x + ", " + temp.y + ")");
        }

        return hull;
    }
}