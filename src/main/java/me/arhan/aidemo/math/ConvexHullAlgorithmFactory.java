package me.arhan.aidemo.math;

import java.util.List;
import java.util.function.Function;

/**
 * Factory for creating convex hull algorithm instances.
 * This factory provides a unified interface for different convex hull algorithm implementations.
 */
public class ConvexHullAlgorithmFactory {

    /**
     * Gets a function that computes the convex hull using the specified algorithm type.
     *
     * @param type The type of algorithm to use
     * @return A function that takes an array of points and returns a list of points forming the convex hull
     */
    public static Function<Point[], List<Point>> getAlgorithm(ConvexHullAlgorithmType type) {
        switch (type) {
            case GIFT_WRAPPING:
                return GiftWrappingAlgorithm::run;
            case GRAHAM_SCAN:
                return GrahamScanAlgorithm::run;
            default:
                throw new IllegalArgumentException("Unknown algorithm type: " + type);
        }
    }
} 
