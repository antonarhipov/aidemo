package me.arhan.aidemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import me.arhan.aidemo.entity.CalculationResult;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for CalculationResult.
 */
public class CalculationResultDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("algorithmType")
    private String algorithmType;

    @JsonProperty("inputPointsCount")
    private int inputPointsCount;

    @JsonProperty("hullPointsCount")
    private int hullPointsCount;

    @JsonProperty("calculationTimeMs")
    private double calculationTimeMs;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    /**
     * Default constructor.
     */
    public CalculationResultDto() {
    }

    /**
     * Constructs a new CalculationResultDto from a CalculationResult entity.
     *
     * @param calculationResult The CalculationResult entity
     */
    public CalculationResultDto(CalculationResult calculationResult) {
        this.id = calculationResult.getId();
        this.algorithmType = calculationResult.getAlgorithmType();
        this.inputPointsCount = calculationResult.getInputPointsCount();
        this.hullPointsCount = calculationResult.getHullPointsCount();
        this.calculationTimeMs = calculationResult.getCalculationTimeMs();
        this.createdAt = calculationResult.getCreatedAt();
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