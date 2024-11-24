package me.arhan.aidemo.custom;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.arhan.aidemo.custom.Orientation.COLLINEAR;
import static me.arhan.aidemo.custom.Orientation.COUNTERCLOCKWISE;
import static me.arhan.aidemo.custom.Orientation.getOrientation;

public class ChansAlgorithm implements Algorithm {
    private static final int INITIAL_GUESS = 4;

    private static void printConvexHull(List<Coordinate> hull) {
        for (Coordinate point : hull) {
            System.out.printf("(%d, %d)%n", point.x, point.y);
        }
    }

    private static List<Coordinate> chanConvexHull(List<Coordinate> points, int m) {
        List<List<Coordinate>> hulls = createSubHulls(points, m);
        Coordinate[] extremePoints = getExtremePoints(hulls);

        Coordinate pointOnHull = findLeftmostPoint(points);
        if (pointOnHull == null) return null;

        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < extremePoints.length; i++) {
            result.add(pointOnHull);
            Coordinate endpoint = findNextHullPoint(pointOnHull, extremePoints);

            if (endpoint.equals(result.get(0))) {
                return result;
            }

            pointOnHull = endpoint;

            if (i == m) return null; // m is too small
        }

        return null; // m is too small
    }

    private static List<List<Coordinate>> createSubHulls(List<Coordinate> points, int m) {
        List<List<Coordinate>> hulls = new ArrayList<>();
        for (int i = 0; i < points.size(); i += m) {
            List<Coordinate> subset = points.subList(i, Math.min(i + m, points.size()));
            hulls.add(grahamScan(subset.toArray(new Coordinate[0])));
        }
        return hulls;
    }

    private static Coordinate[] getExtremePoints(List<List<Coordinate>> hulls) {
        int totalPoints = hulls.stream().mapToInt(List::size).sum();
        Coordinate[] extremePoints = new Coordinate[totalPoints];
        int idx = 0;
        for (List<Coordinate> hull : hulls) {
            for (Coordinate point : hull) {
                extremePoints[idx++] = point;
            }
        }
        return extremePoints;
    }

    private static Coordinate findLeftmostPoint(List<Coordinate> points) {
        return points.stream().min(Comparator.comparingInt(p -> p.x)).orElse(null);
    }

    private static Coordinate findNextHullPoint(Coordinate pointOnHull, Coordinate[] extremePoints) {
        Coordinate endpoint = extremePoints[0];
        for (int j = 1; j < extremePoints.length; j++) {
            if (endpoint.equals(pointOnHull) || getOrientation(pointOnHull, endpoint, extremePoints[j]) == COUNTERCLOCKWISE) {
                endpoint = extremePoints[j];
            }
        }
        return endpoint;
    }

    private static List<Coordinate> grahamScan(Coordinate[] points) {
        if (points.length < 3) return Arrays.asList(points);

        Coordinate anchor = findAnchorPoint(points);
        sortPointsByPolarAngle(points, anchor);

        Deque<Coordinate> hull = new ArrayDeque<>();
        hull.push(points[0]);
        hull.push(points[1]);

        for (int i = 2; i < points.length; i++) {
            while (hull.size() > 1 && getOrientation(hull.peek(), hull.peekFirst(), points[i]) != COUNTERCLOCKWISE) {
                hull.pop();
            }
            hull.push(points[i]);
        }

        return new ArrayList<>(hull);
    }

    private static Coordinate findAnchorPoint(Coordinate[] points) {
        Coordinate anchor = points[0];
        for (Coordinate point : points) {
            if (point.y < anchor.y || (point.y == anchor.y && point.x < anchor.x)) {
                anchor = point;
            }
        }
        return anchor;
    }

    private static void sortPointsByPolarAngle(Coordinate[] points, Coordinate anchor) {
        Arrays.sort(points, (a, b) -> {
            Orientation orientation = getOrientation(anchor, a, b);
            if (orientation == COLLINEAR) {
                return Double.compare(distanceSquared(anchor, b), distanceSquared(anchor, a));
            }
            return orientation == COUNTERCLOCKWISE ? -1 : 1;
        });
    }

    private static double distanceSquared(Coordinate a, Coordinate b) {
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        return dx * dx + dy * dy;
    }

    public @NotNull List<Coordinate> run(@NotNull List<Coordinate> dts) {
        if (dts.size() < MINIMUM_POINTS_FOR_HULL) return Collections.emptyList();

        int m = INITIAL_GUESS;
        List<Coordinate> hull;

        while (true) {
            hull = chanConvexHull(dts, m);
            if (hull != null) break;
            m *= m;
        }

        printConvexHull(hull);
        return hull;
    }
}
