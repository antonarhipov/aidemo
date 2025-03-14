package me.arhan.aidemo.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Implementation of the Convex Hull algorithm using Graham's scan approach.
 * This algorithm has a time complexity of O(n log n) where n is the number of points.
 */
public class GrahamScanAlgorithm {
    
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
     * Computes the convex hull of a set of points using Graham's scan algorithm.
     * This algorithm has a time complexity of O(n log n) where n is the number of points.
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
        
        // Find the point with the lowest y-coordinate (and leftmost if tied)
        int lowestPoint = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].y < points[lowestPoint].y || 
                (points[i].y == points[lowestPoint].y && points[i].x < points[lowestPoint].x)) {
                lowestPoint = i;
            }
        }
        
        // Swap the lowest point with the first point
        Point temp = points[0];
        points[0] = points[lowestPoint];
        points[lowestPoint] = temp;
        
        // Sort the remaining points by polar angle with respect to the lowest point
        final Point pivot = points[0];
        Arrays.sort(points, 1, points.length, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int orientation = calculateOrientation(pivot, p1, p2);
                if (orientation == 0) {
                    // If collinear, sort by distance from pivot
                    return Integer.compare(
                        distanceSquared(pivot, p1),
                        distanceSquared(pivot, p2)
                    );
                }
                return orientation == 2 ? -1 : 1;
            }
        });
        
        // Remove collinear points with the same angle (keep only the farthest)
        int m = 1;
        for (int i = 1; i < points.length; i++) {
            while (i < points.length - 1 && 
                   calculateOrientation(pivot, points[i], points[i + 1]) == 0) {
                i++;
            }
            points[m++] = points[i];
        }
        
        // If we have fewer than 3 points after removing collinear points, return empty list
        if (m < 3) {
            return Collections.emptyList();
        }
        
        // Build the convex hull using a stack
        Stack<Point> hull = new Stack<>();
        hull.push(points[0]);
        hull.push(points[1]);
        hull.push(points[2]);
        
        for (int i = 3; i < m; i++) {
            // Remove points that make a non-left turn
            while (hull.size() > 1 && 
                   calculateOrientation(hull.get(hull.size() - 2), hull.peek(), points[i]) != 2) {
                hull.pop();
            }
            hull.push(points[i]);
        }
        
        return new ArrayList<>(hull);
    }
    
    /**
     * Calculates the squared Euclidean distance between two points.
     * 
     * @param p1 First point
     * @param p2 Second point
     * @return The squared distance between the points
     */
    private static int distanceSquared(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }
} 