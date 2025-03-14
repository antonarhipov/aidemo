package me.arhan.aidemo.repository;

import me.arhan.aidemo.entity.CalculationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for calculation results.
 */
@Repository
public interface CalculationResultRepository extends JpaRepository<CalculationResult, Long> {

    /**
     * Finds all calculation results ordered by creation time (descending).
     *
     * @return A list of calculation results
     */
    @Query("SELECT c FROM CalculationResult c ORDER BY c.createdAt DESC")
    List<CalculationResult> findAllOrderByCreatedAtDesc();

    /**
     * Finds calculation results by algorithm type.
     *
     * @param algorithmType The algorithm type
     * @return A list of calculation results
     */
    List<CalculationResult> findByAlgorithmType(String algorithmType);

    /**
     * Calculates the average calculation time for a specific algorithm type.
     *
     * @param algorithmType The algorithm type
     * @return The average calculation time in milliseconds
     */
    @Query("SELECT AVG(c.calculationTimeMs) FROM CalculationResult c WHERE c.algorithmType = :algorithmType")
    Double calculateAverageCalculationTimeByAlgorithmType(@Param("algorithmType") String algorithmType);
} 