package me.arhan.aidemo.math;

/**
 * Enum representing the different types of convex hull algorithms.
 */
public enum ConvexHullAlgorithmType {
    /**
     * Gift Wrapping (Jarvis March) algorithm.
     * Time complexity: O(n*h) where n is the number of points and h is the number of points on the hull.
     */
    GIFT_WRAPPING,
    
    /**
     * Graham's Scan algorithm.
     * Time complexity: O(n log n) where n is the number of points.
     */
    GRAHAM_SCAN
} 