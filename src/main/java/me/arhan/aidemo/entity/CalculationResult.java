package me.arhan.aidemo.entity;

import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Entity for storing calculation results.
 */
@Entity
@Table(name = "calculation_results")
public class CalculationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "algorithm_type")
    private String algorithmType;

    @Column(name = "input_points_count")
    private int inputPointsCount;

    @Column(name = "hull_points_count")
    private int hullPointsCount;

    @Column(name = "calculation_time_ms")
    private double calculationTimeMs;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Default constructor.
     */
    public CalculationResult() {
    }

    /**
     * Constructs a new CalculationResult with the specified values.
     *
     * @param algorithmType The algorithm type used for the calculation
     * @param inputPointsCount The number of input points
     * @param hullPointsCount The number of hull points
     * @param calculationTimeMs The calculation time in milliseconds
     */
    public CalculationResult(ConvexHullAlgorithmType algorithmType, int inputPointsCount, int hullPointsCount, double calculationTimeMs) {
        this.algorithmType = algorithmType.name();
        this.inputPointsCount = inputPointsCount;
        this.hullPointsCount = hullPointsCount;
        this.calculationTimeMs = calculationTimeMs;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public int getInputPointsCount() {
        return inputPointsCount;
    }

    public void setInputPointsCount(int inputPointsCount) {
        this.inputPointsCount = inputPointsCount;
    }

    public int getHullPointsCount() {
        return hullPointsCount;
    }

    public void setHullPointsCount(int hullPointsCount) {
        this.hullPointsCount = hullPointsCount;
    }

    public double getCalculationTimeMs() {
        return calculationTimeMs;
    }

    public void setCalculationTimeMs(double calculationTimeMs) {
        this.calculationTimeMs = calculationTimeMs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 