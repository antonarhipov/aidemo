package me.arhan.aidemo.service;

import me.arhan.aidemo.entity.CalculationResult;
import me.arhan.aidemo.math.ConvexHullAlgorithmFactory;
import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import me.arhan.aidemo.math.Point;
import me.arhan.aidemo.repository.CalculationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * Service for computing convex hulls of sets of points.
 * This service encapsulates the business logic for convex hull calculations.
 */
@Service
public class ConvexHullService {

    @Value("${convexhull.algorithm.default:GRAHAM_SCAN}")
    private String defaultAlgorithmName;
    
    private final CalculationResultRepository calculationResultRepository;
    
    /**
     * Constructs a new ConvexHullService with the specified repository.
     *
     * @param calculationResultRepository The repository for calculation results
     */
    @Autowired
    public ConvexHullService(CalculationResultRepository calculationResultRepository) {
        this.calculationResultRepository = calculationResultRepository;
    }

    /**
     * Computes the convex hull for a given list of points using the default algorithm.
     *
     * @param points The list of points for which to compute the convex hull
     * @return A list of points forming the convex hull
     * @throws IllegalArgumentException if the input is invalid
     */
    public List<Point> computeConvexHull(List<Point> points) {
        return computeConvexHull(points, getDefaultAlgorithmType());
    }
    
    /**
     * Computes the convex hull for a given list of points using the specified algorithm.
     *
     * @param points The list of points for which to compute the convex hull
     * @param algorithmType The type of algorithm to use
     * @return A list of points forming the convex hull
     * @throws IllegalArgumentException if the input is invalid
     */
    public List<Point> computeConvexHull(List<Point> points, ConvexHullAlgorithmType algorithmType) {
        // Validate input
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty");
        }
        
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Points in the list cannot be null");
            }
        }
        
        // Get the algorithm function from the factory
        Function<Point[], List<Point>> algorithm = ConvexHullAlgorithmFactory.getAlgorithm(algorithmType);
        
        // Measure calculation time
        long startTime = System.nanoTime();
        
        // Convert list to array and compute the convex hull
        Point[] input = points.toArray(new Point[0]);
        List<Point> result = algorithm.apply(input);
        
        // Calculate elapsed time in milliseconds
        long endTime = System.nanoTime();
        double calculationTimeMs = (endTime - startTime) / 1_000_000.0;
        
        // Store calculation result
        CalculationResult calculationResult = new CalculationResult(
                algorithmType,
                points.size(),
                result.size(),
                calculationTimeMs
        );
        calculationResultRepository.save(calculationResult);
        
        return result;
    }
    
    /**
     * Gets all calculation results ordered by creation time (descending).
     *
     * @return A list of calculation results
     */
    public List<CalculationResult> getAllCalculationResults() {
        return calculationResultRepository.findAllOrderByCreatedAtDesc();
    }
    
    /**
     * Gets calculation results by algorithm type.
     *
     * @param algorithmType The algorithm type
     * @return A list of calculation results
     */
    public List<CalculationResult> getCalculationResultsByAlgorithmType(ConvexHullAlgorithmType algorithmType) {
        return calculationResultRepository.findByAlgorithmType(algorithmType.name());
    }
    
    /**
     * Calculates the average calculation time for a specific algorithm type.
     *
     * @param algorithmType The algorithm type
     * @return The average calculation time in milliseconds, or null if no results are available
     */
    public Double calculateAverageCalculationTime(ConvexHullAlgorithmType algorithmType) {
        return calculationResultRepository.calculateAverageCalculationTimeByAlgorithmType(algorithmType.name());
    }
    
    /**
     * Gets the default algorithm type from the configuration.
     *
     * @return The default algorithm type
     */
    private ConvexHullAlgorithmType getDefaultAlgorithmType() {
        try {
            return ConvexHullAlgorithmType.valueOf(defaultAlgorithmName);
        } catch (IllegalArgumentException e) {
            // Fallback to GRAHAM_SCAN if the configured value is invalid
            return ConvexHullAlgorithmType.GRAHAM_SCAN;
        }
    }
} 