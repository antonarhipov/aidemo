package me.arhan.aidemo.custom;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.arhan.aidemo.custom.Orientation.CLOCKWISE;
import static me.arhan.aidemo.custom.Orientation.COUNTERCLOCKWISE;
import static me.arhan.aidemo.custom.Orientation.getOrientation;

public class Quickhull implements Algorithm {

    private static void findHull(List<Coordinate> points, Coordinate p1, Coordinate p2, Set<Coordinate> hull) {
        if (points.isEmpty()) return;

        if (points.size() == 1) {
            hull.add(points.get(0));
            return;
        }

        int maxDistance = 0;
        Coordinate farthest = null;

        for (Coordinate point : points) {
            int distance = distanceFromLine(p1, p2, point);
            if (distance > maxDistance) {
                maxDistance = distance;
                farthest = point;
            }
        }

        if (farthest != null) {
            hull.add(farthest);

            List<Coordinate> leftSet = new ArrayList<>();
            List<Coordinate> rightSet = new ArrayList<>();

            for (Coordinate point : points) {
                if (getOrientation(p1, farthest, point) == COUNTERCLOCKWISE) {
                    leftSet.add(point);
                }
                if (getOrientation(farthest, p2, point) == COUNTERCLOCKWISE) {
                    rightSet.add(point);
                }
            }

            findHull(leftSet, p1, farthest, hull);
            findHull(rightSet, farthest, p2, hull);
        }
    }

    private static int distanceFromLine(Coordinate a, Coordinate b, Coordinate p) {
        return Math.abs((b.y - a.y) * p.x - (b.x - a.x) * p.y + b.x * a.y - b.y * a.x);
    }

    private static Coordinate findCenter(List<Coordinate> points) {
        int x = 0, y = 0;
        for (Coordinate point : points) {
            x += point.x;
            y += point.y;
        }
        return new Coordinate(x / points.size(), y / points.size());
    }

    public @NotNull List<Coordinate> run(@NotNull List<Coordinate> dts) {
        if (dts.size() < MINIMUM_POINTS_FOR_HULL) return Collections.emptyList();

        Set<Coordinate> hullSet = new HashSet<>();

        // Find the points with minimum and maximum x coordinates
        Coordinate minX = dts.get(0), maxX = dts.get(0);
        for (Coordinate point : dts) {
            if (point.x < minX.x) minX = point;
            if (point.x > maxX.x) maxX = point;
        }

        hullSet.add(minX);
        hullSet.add(maxX);

        List<Coordinate> leftSet = new ArrayList<>();
        List<Coordinate> rightSet = new ArrayList<>();

        for (Coordinate point : dts) {
            if (getOrientation(minX, maxX, point) == COUNTERCLOCKWISE) {
                leftSet.add(point);
            } else if (getOrientation(minX, maxX, point) == CLOCKWISE) {
                rightSet.add(point);
            }
        }

        findHull(leftSet, minX, maxX, hullSet);
        findHull(rightSet, maxX, minX, hullSet);

        List<Coordinate> hull = new ArrayList<>(hullSet);

        // Sort the hull points clockwise
        final Coordinate center = findCenter(hull);
        hull.sort((a, b) -> {
            double thetaA = Math.atan2(a.y - center.y, a.x - center.x);
            double thetaB = Math.atan2(b.y - center.y, b.x - center.x);
            return Double.compare(thetaA, thetaB);
        });

        // Print the points in the convex hull
        for (Coordinate temp : hull) {
            System.out.println("(" + temp.x + ", " + temp.y + ")");
        }

        return hull;
    }

}



