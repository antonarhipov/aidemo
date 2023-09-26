package me.arhan.aidemo.math;

import java.util.ArrayList;
import java.util.List;

public class TheAlgorithm {
    public static List<D> run(D[] d) {
        return ConvexHullAlgorithm.computeConvexHull(d);
    }
}

class ConvexHullAlgorithm {
    private static final int CLOCKWISE = 2;

    private static int calculateOrientation(D point1, D point2, D point3) {
        int value = (point2.y - point1.y) * (point3.x - point2.x) -
                    (point2.x - point1.x) * (point3.y - point2.y);

        if (value == 0) return 0;
        return (value > 0) ? 1 : CLOCKWISE;
    }

    public static List<D> computeConvexHull(D[] points) {
        if (points.length < 3) return null;
        List<D> hullPoints = new ArrayList<>();
        int l = findPointWithSmallestXCoordinate(points);
        int p = l, q;

        do {
            hullPoints.add(points[p]);
            q = (p + 1) % points.length;
            for (int i = 0; i < points.length; i++) {
                if (calculateOrientation(points[p], points[i], points[q]) == CLOCKWISE)
                    q = i;
            }
            p = q;
        } while (p != l);

        printPoints(hullPoints);
        return hullPoints;
    }

    private static int findPointWithSmallestXCoordinate(D[] points) {
        int leftMost = 0;
        for (int i = 1; i < points.length; i++)
            if (points[i].x < points[leftMost].x)
                leftMost = i;

        return leftMost;
    }

    private static void printPoints(List<D> points) {
        for (D point : points)
            System.out.println("(" + point.x + ", " + point.y + ")");
    }
}
