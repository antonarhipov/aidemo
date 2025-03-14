package me.arhan.aidemo.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the convex hull algorithm.
 */
class TheAlgorithmTest {

    @Test
    @DisplayName("Calculate orientation of three points")
    void testCalculateOrientation() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(4, 0);
        Point p3 = new Point(2, 2);
        
        // Points in counterclockwise order
        assertEquals(2, TheAlgorithm.calculateOrientation(p1, p2, p3));
        
        // Points in clockwise order
        assertEquals(1, TheAlgorithm.calculateOrientation(p1, p3, p2));
        
        // Collinear points
        Point p4 = new Point(2, 0);
        assertEquals(0, TheAlgorithm.calculateOrientation(p1, p4, p2));
    }
    
    @Test
    @DisplayName("Calculate orientation with null points should throw exception")
    void testCalculateOrientationWithNullPoints() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(4, 0);
        
        assertThrows(IllegalArgumentException.class, () -> TheAlgorithm.calculateOrientation(null, p1, p2));
        assertThrows(IllegalArgumentException.class, () -> TheAlgorithm.calculateOrientation(p1, null, p2));
        assertThrows(IllegalArgumentException.class, () -> TheAlgorithm.calculateOrientation(p1, p2, null));
    }
    
    @Test
    @DisplayName("Compute convex hull of a square")
    void testConvexHullOfSquare() {
        Point[] points = {
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(1, 0)
        };
        
        List<Point> hull = TheAlgorithm.run(points);
        
        assertEquals(4, hull.size());
        assertTrue(containsPoint(hull, 0, 0));
        assertTrue(containsPoint(hull, 0, 1));
        assertTrue(containsPoint(hull, 1, 1));
        assertTrue(containsPoint(hull, 1, 0));
    }
    
    @Test
    @DisplayName("Compute convex hull with points inside the hull")
    void testConvexHullWithInnerPoints() {
        Point[] points = {
            new Point(0, 0),   // Hull point
            new Point(0, 4),   // Hull point
            new Point(4, 0),   // Hull point
            new Point(4, 4),   // Hull point
            new Point(1, 1),   // Inner point
            new Point(2, 2),   // Inner point
            new Point(3, 1)    // Inner point
        };
        
        List<Point> hull = TheAlgorithm.run(points);
        
        assertEquals(4, hull.size());
        assertTrue(containsPoint(hull, 0, 0));
        assertTrue(containsPoint(hull, 0, 4));
        assertTrue(containsPoint(hull, 4, 0));
        assertTrue(containsPoint(hull, 4, 4));
        assertFalse(containsPoint(hull, 1, 1));
        assertFalse(containsPoint(hull, 2, 2));
        assertFalse(containsPoint(hull, 3, 1));
    }
    
    @Test
    @DisplayName("Compute convex hull with fewer than 3 points")
    void testConvexHullWithFewerThan3Points() {
        Point[] points1 = {
            new Point(0, 0),
            new Point(1, 1)
        };
        
        List<Point> hull1 = TheAlgorithm.run(points1);
        assertTrue(hull1.isEmpty());
        
        Point[] points2 = {
            new Point(0, 0)
        };
        
        List<Point> hull2 = TheAlgorithm.run(points2);
        assertTrue(hull2.isEmpty());
    }
    
    @Test
    @DisplayName("Compute convex hull with null input should throw exception")
    void testConvexHullWithNullInput() {
        assertThrows(IllegalArgumentException.class, () -> TheAlgorithm.run(null));
        
        Point[] pointsWithNull = {
            new Point(0, 0),
            null,
            new Point(1, 1)
        };
        
        assertThrows(IllegalArgumentException.class, () -> TheAlgorithm.run(pointsWithNull));
    }
    
    /**
     * Helper method to check if a list of points contains a point with specific coordinates.
     */
    private boolean containsPoint(List<Point> points, int x, int y) {
        return points.stream().anyMatch(p -> p.x == x && p.y == y);
    }
} 