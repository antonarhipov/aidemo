package me.arhan.aidemo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import me.arhan.aidemo.math.Point;
import me.arhan.aidemo.service.ConvexHullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for computing convex hulls.
 */
@RestController
@RequestMapping("/api/convexhull")
@Tag(name = "Convex Hull", description = "API for computing convex hulls of sets of points")
public class AlgorithmController {

    private final ConvexHullService convexHullService;

    /**
     * Constructs a new AlgorithmController with the specified service.
     *
     * @param convexHullService The service for convex hull calculations
     */
    @Autowired
    public AlgorithmController(ConvexHullService convexHullService) {
        this.convexHullService = convexHullService;
    }

    /**
     * Computes the convex hull for a given list of points using the default algorithm.
     *
     * @param points The list of points for which to compute the convex hull
     * @return A list of points forming the convex hull
     */
    @PostMapping
    @Operation(
        summary = "Compute convex hull with default algorithm",
        description = "Computes the convex hull for a given list of points using the default algorithm"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully computed convex hull",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Point.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<List<Point>> createConvexHull(
            @Parameter(description = "List of points for which to compute the convex hull", required = true)
            @RequestBody List<Point> points) {
        try {
            List<Point> result = convexHullService.computeConvexHull(points);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Computes the convex hull for a given list of points using the specified algorithm.
     *
     * @param points The list of points for which to compute the convex hull
     * @param algorithm The algorithm to use
     * @return A list of points forming the convex hull
     */
    @PostMapping("/{algorithm}")
    @Operation(
        summary = "Compute convex hull with specified algorithm",
        description = "Computes the convex hull for a given list of points using the specified algorithm"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully computed convex hull",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Point.class))
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data or algorithm")
    })
    public ResponseEntity<List<Point>> createConvexHullWithAlgorithm(
            @Parameter(description = "List of points for which to compute the convex hull", required = true)
            @RequestBody List<Point> points,
            @Parameter(description = "Algorithm to use (GIFT_WRAPPING or GRAHAM_SCAN)", required = true)
            @PathVariable String algorithm) {
        try {
            ConvexHullAlgorithmType algorithmType = ConvexHullAlgorithmType.valueOf(algorithm.toUpperCase());
            List<Point> result = convexHullService.computeConvexHull(points, algorithmType);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Gets the available convex hull algorithms.
     *
     * @return A map of algorithm names to descriptions
     */
    @GetMapping("/algorithms")
    @Operation(
        summary = "Get available algorithms",
        description = "Returns a list of available convex hull algorithms"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved algorithms",
            content = @Content(mediaType = "application/json")
        )
    })
    public ResponseEntity<Map<String, String>> getAvailableAlgorithms() {
        Map<String, String> algorithms = Map.of(
                "GIFT_WRAPPING", "Gift Wrapping (Jarvis March) algorithm",
                "GRAHAM_SCAN", "Graham's Scan algorithm"
        );
        return ResponseEntity.ok(algorithms);
    }
}
