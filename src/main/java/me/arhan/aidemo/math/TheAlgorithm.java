package me.arhan.aidemo.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the Convex Hull algorithm using the Gift Wrapping (Jarvis March) approach.
 * The convex hull is the smallest convex polygon that contains all the points in a given set.
 */
public class TheAlgorithm {

    /**
     * Calculates the orientation of three points (p, q, r).
     * 
     * @param p First point
     * @param q Second point
     * @param r Third point
     * @return 0 if collinear, 1 if clockwise, 2 if counterclockwise
     * @throws IllegalArgumentException if any of the points is null
     */
    public static int calculateOrientation(Point p, Point q, Point r) {
        if (p == null || q == null || r == null) {
            throw new IllegalArgumentException("Points cannot be null");
        }
        
        int val = (q.y - p.y) * (r.x - q.x) -
                  (q.x - p.x) * (r.y - q.y);

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    /**
     * Computes the convex hull of a set of points using the Gift Wrapping algorithm.
     * This algorithm has a time complexity of O(n*h) where n is the number of points
     * and h is the number of points on the hull.
     *
     * @param points Array of points for which to compute the convex hull
     * @return List of points forming the convex hull in counterclockwise order,
     *         or an empty list if fewer than 3 points are provided
     * @throws IllegalArgumentException if the input array is null or contains null elements
     */
    public static List<Point> run(Point[] points) {
        // Validate input
        if (points == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Points in the array cannot be null");
            }
        }
        
        // Handle cases with fewer than 3 points
        if (points.length < 3) {
            return Collections.emptyList();
        }

        List<Point> result = new ArrayList<>();

        // Find the leftmost point
        int l = 0;
        int l1 = l;
        for (int i1 = 1; i1 < points.length; i1++)
            if (points[i1].x < points[l1].x)
                l1 = i1;
        l = l1;

        // Start from leftmost point, keep moving counterclockwise
        // until reaching the start point again
        int p = l, q;
        do {
            // Add current point to result
            result.add(points[p]);
            
            // Search for a point 'q' such that orientation(p, i, q) is counterclockwise
            // for all points 'i'
            q = (p + 1) % points.length;

            for (int i = 0; i < points.length; i++) {
                if (calculateOrientation(points[p], points[i], points[q]) == 2)
                    q = i;
            }

            // q is the most counterclockwise with respect to p
            // Set p as q for next iteration
            p = q;

        } while (p != l);  // Continue until we reach the first point again

        // Print the points of the convex hull
        for (Point temp : result)
            System.out.println("(" + temp.x + ", " + temp.y + ")");

        return result;
    }

}