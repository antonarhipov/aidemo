package me.arhan.aidemo.custom;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.arhan.aidemo.custom.Orientation.getOrientation;

public class GrahamScan implements Algorithm {
    private static Coordinate findAnchorPoint(List<Coordinate> dts) {
        return dts.stream()
                .min(Comparator.comparingInt(Coordinate::getY)
                        .thenComparingInt(Coordinate::getX))
                .orElseThrow();
    }

    private static void sortPointsByPolarAngle(List<Coordinate> dts, Coordinate anchor) {
        dts.sort((a, b) -> {
            Orientation orientation = getOrientation(anchor, a, b);
            if (orientation == Orientation.COLLINEAR) {
                return Double.compare(distanceSquared(anchor, b), distanceSquared(anchor, a));
            }
            return orientation == Orientation.COUNTERCLOCKWISE ? -1 : 1;
        });
    }

    private static List<Coordinate> buildConvexHull(List<Coordinate> dts) {
        Deque<Coordinate> hull = new ArrayDeque<>();
        hull.push(dts.get(0));
        hull.push(dts.get(1));

        for (int i = 2; i < dts.size(); i++) {
            while (hull.size() > 1 && getOrientation(hull.getFirst(), hull.peek(), dts.get(i)) != Orientation.COUNTERCLOCKWISE) {
                hull.pop();
            }
            hull.push(dts.get(i));
        }

        List<Coordinate> result = new ArrayList<>(hull);
        printConvexHull(result);
        return result;
    }

    private static double distanceSquared(Coordinate a, Coordinate b) {
        int dx = b.getX() - a.getX();
        int dy = b.getY() - a.getY();
        return dx * dx + dy * dy;
    }

    private static void printConvexHull(List<Coordinate> hull) {
        hull.forEach(point -> System.out.printf("(%d, %d)%n", point.getX(), point.getY()));
    }

    public @NotNull List<Coordinate> run(@NotNull List<Coordinate> dts) {
        if (dts.size() < MINIMUM_POINTS_FOR_HULL) return Collections.emptyList();

        Coordinate anchor = findAnchorPoint(dts);
        sortPointsByPolarAngle(dts, anchor);
        return buildConvexHull(dts);
    }
}