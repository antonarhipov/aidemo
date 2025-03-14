package me.arhan.aidemo.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Performance tests for the convex hull algorithms.
 * These tests compare the performance of different convex hull algorithms.
 */
public class AlgorithmPerformanceTest {
    
    private static final int SMALL_DATASET_SIZE = 100;
    private static final int MEDIUM_DATASET_SIZE = 1000;
    private static final int LARGE_DATASET_SIZE = 10000;
    private static final int COORDINATE_RANGE = 1000;
    private static final int WARMUP_ITERATIONS = 5;
    private static final int TEST_ITERATIONS = 10;
    
    @Test
    @DisplayName("Compare performance of convex hull algorithms")
    public void compareAlgorithmPerformance(TestInfo testInfo) {
        // Print test name to ensure output is visible
        System.err.println("Running test: " + testInfo.getDisplayName());
        
        System.err.println("\n=== Convex Hull Algorithm Performance Comparison ===");
        
        // Get algorithm functions
        Function<Point[], List<Point>> giftWrapping = ConvexHullAlgorithmFactory.getAlgorithm(ConvexHullAlgorithmType.GIFT_WRAPPING);
        Function<Point[], List<Point>> grahamScan = ConvexHullAlgorithmFactory.getAlgorithm(ConvexHullAlgorithmType.GRAHAM_SCAN);
        
        // Test with small dataset
        System.err.println("\nSmall dataset (" + SMALL_DATASET_SIZE + " points):");
        Point[] smallDataset = generateRandomPoints(SMALL_DATASET_SIZE);
        compareAlgorithms(smallDataset, giftWrapping, grahamScan);
        
        // Test with medium dataset
        System.err.println("\nMedium dataset (" + MEDIUM_DATASET_SIZE + " points):");
        Point[] mediumDataset = generateRandomPoints(MEDIUM_DATASET_SIZE);
        compareAlgorithms(mediumDataset, giftWrapping, grahamScan);
        
        // Test with large dataset
        System.err.println("\nLarge dataset (" + LARGE_DATASET_SIZE + " points):");
        Point[] largeDataset = generateRandomPoints(LARGE_DATASET_SIZE);
        compareAlgorithms(largeDataset, giftWrapping, grahamScan);
        
        System.err.println("\n=== Performance Test Completed ===");
    }
    
    /**
     * Compares the performance of two algorithms on the same dataset.
     * 
     * @param points The dataset to use
     * @param giftWrapping The Gift Wrapping algorithm function
     * @param grahamScan The Graham's Scan algorithm function
     */
    private void compareAlgorithms(
            Point[] points, 
            Function<Point[], List<Point>> giftWrapping,
            Function<Point[], List<Point>> grahamScan) {
        
        System.err.println("  Warming up algorithms...");
        
        // Warm up
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            giftWrapping.apply(points.clone());
            grahamScan.apply(points.clone());
        }
        
        System.err.println("  Running performance tests...");
        
        // Test Gift Wrapping
        long giftWrappingStart = System.nanoTime();
        List<Point> giftWrappingResult = null;
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            giftWrappingResult = giftWrapping.apply(points.clone());
        }
        long giftWrappingEnd = System.nanoTime();
        long giftWrappingTime = (giftWrappingEnd - giftWrappingStart) / TEST_ITERATIONS;
        
        // Test Graham's Scan
        long grahamScanStart = System.nanoTime();
        List<Point> grahamScanResult = null;
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            grahamScanResult = grahamScan.apply(points.clone());
        }
        long grahamScanEnd = System.nanoTime();
        long grahamScanTime = (grahamScanEnd - grahamScanStart) / TEST_ITERATIONS;
        
        // Print results
        System.err.println("  Gift Wrapping: " + giftWrappingTime / 1_000_000.0 + " ms, " + 
                           giftWrappingResult.size() + " hull points");
        System.err.println("  Graham's Scan: " + grahamScanTime / 1_000_000.0 + " ms, " + 
                           grahamScanResult.size() + " hull points");
        System.err.println("  Speedup: " + String.format("%.2f", (double) giftWrappingTime / grahamScanTime) + "x");
    }
    
    /**
     * Generates an array of random points.
     * 
     * @param size The number of points to generate
     * @return An array of random points
     */
    private Point[] generateRandomPoints(int size) {
        Random random = new Random(42); // Fixed seed for reproducibility
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            int x = random.nextInt(COORDINATE_RANGE);
            int y = random.nextInt(COORDINATE_RANGE);
            points[i] = new Point(x, y);
        }
        return points;
    }
} 