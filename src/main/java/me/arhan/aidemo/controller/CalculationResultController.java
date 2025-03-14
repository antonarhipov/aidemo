package me.arhan.aidemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.arhan.aidemo.dto.CalculationResultDto;
import me.arhan.aidemo.entity.CalculationResult;
import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import me.arhan.aidemo.service.ConvexHullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for accessing calculation results.
 */
@RestController
@RequestMapping("/api/results")
@Tag(name = "Calculation Results", description = "API for accessing convex hull calculation results")
public class CalculationResultController {

    private final ConvexHullService convexHullService;

    /**
     * Constructs a new CalculationResultController with the specified service.
     *
     * @param convexHullService The service for convex hull calculations
     */
    @Autowired
    public CalculationResultController(ConvexHullService convexHullService) {
        this.convexHullService = convexHullService;
    }

    /**
     * Gets all calculation results.
     *
     * @return A list of calculation result DTOs
     */
    @GetMapping
    @Operation(summary = "Get all calculation results", description = "Retrieves all convex hull calculation results ordered by creation time (descending)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved calculation results")
    })
    public ResponseEntity<List<CalculationResultDto>> getAllResults() {
        List<CalculationResult> results = convexHullService.getAllCalculationResults();
        List<CalculationResultDto> resultDtos = results.stream()
                .map(CalculationResultDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultDtos);
    }

    /**
     * Gets calculation results by algorithm type.
     *
     * @param algorithmType The algorithm type
     * @return A list of calculation result DTOs
     */
    @GetMapping("/algorithm/{algorithmType}")
    @Operation(summary = "Get results by algorithm", description = "Retrieves convex hull calculation results for a specific algorithm type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved calculation results"),
            @ApiResponse(responseCode = "400", description = "Invalid algorithm type")
    })
    public ResponseEntity<List<CalculationResultDto>> getResultsByAlgorithm(@PathVariable String algorithmType) {
        try {
            ConvexHullAlgorithmType type = ConvexHullAlgorithmType.valueOf(algorithmType.toUpperCase());
            List<CalculationResult> results = convexHullService.getCalculationResultsByAlgorithmType(type);
            List<CalculationResultDto> resultDtos = results.stream()
                    .map(CalculationResultDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resultDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Gets average calculation times for all algorithm types.
     *
     * @return A map of algorithm types to their average calculation times
     */
    @GetMapping("/stats/average-times")
    @Operation(summary = "Get average calculation times", description = "Retrieves average calculation times for all algorithm types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved average calculation times")
    })
    public ResponseEntity<Map<String, Double>> getAverageCalculationTimes() {
        Map<String, Double> averageTimes = java.util.Arrays.stream(ConvexHullAlgorithmType.values())
                .collect(Collectors.toMap(
                        Enum::name,
                        type -> convexHullService.calculateAverageCalculationTime(type)
                ));
        return ResponseEntity.ok(averageTimes);
    }
} 