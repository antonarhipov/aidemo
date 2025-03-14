package me.arhan.aidemo.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the Convex Hull algorithm using the Gift Wrapping (Jarvis March) approach.
 * The convex hull is the smallest convex polygon that contains all the points in a given set.
 * 
 * This algorithm has a time complexity of O(n*h) where n is the number of points
 * and h is the number of points on the hull.
 */
public class GiftWrappingAlgorithm {
    
    private static final Logger logger = LoggerFactory.getLogger(GiftWrappingAlgorithm.class);

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
        
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                  (q.getX() - p.getX()) * (r.getY() - q.getY());

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
            logger.info("Input contains fewer than 3 points, returning empty hull");
            return Collections.emptyList();
        }

        logger.debug("Computing convex hull for {} points", points.length);
        List<Point> result = new ArrayList<>();

        // Find the leftmost point
        int leftmostIndex = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].getX() < points[leftmostIndex].getX()) {
                leftmostIndex = i;
            }
        }
        
        logger.debug("Leftmost point: ({}, {})", points[leftmostIndex].getX(), points[leftmostIndex].getY());

        // Start from leftmost point, keep moving counterclockwise
        // until reaching the start point again
        int currentIndex = leftmostIndex;
        int nextIndex;
        
        do {
            // Add current point to result
            result.add(points[currentIndex]);
            logger.debug("Added point to hull: ({}, {})", 
                    points[currentIndex].getX(), points[currentIndex].getY());
            
            // Search for a point 'nextIndex' such that orientation(currentIndex, i, nextIndex) is counterclockwise
            // for all points 'i'
            nextIndex = (currentIndex + 1) % points.length;

            for (int i = 0; i < points.length; i++) {
                if (calculateOrientation(points[currentIndex], points[i], points[nextIndex]) == 2) {
                    nextIndex = i;
                }
            }

            // nextIndex is the most counterclockwise with respect to currentIndex
            // Set currentIndex as nextIndex for next iteration
            currentIndex = nextIndex;

        } while (currentIndex != leftmostIndex);  // Continue until we reach the first point again

        logger.info("Computed convex hull with {} points", result.size());
        
        return result;
    }
}