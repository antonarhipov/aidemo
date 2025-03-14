package me.arhan.aidemo.repository;

import me.arhan.aidemo.entity.CalculationResult;
import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CalculationResultRepositoryTest {

    @Autowired
    private CalculationResultRepository calculationResultRepository;

    @Test
    public void testSaveAndFindAll() {
        // Create test data
        CalculationResult result1 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                100,
                10,
                5.5
        );
        
        CalculationResult result2 = new CalculationResult(
                ConvexHullAlgorithmType.GIFT_WRAPPING,
                200,
                15,
                8.2
        );
        
        // Save test data
        calculationResultRepository.save(result1);
        calculationResultRepository.save(result2);
        
        // Find all results
        List<CalculationResult> results = calculationResultRepository.findAllOrderByCreatedAtDesc();
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.get(0).getCreatedAt().compareTo(results.get(1).getCreatedAt()) >= 0);
    }
    
    @Test
    public void testFindByAlgorithmType() {
        // Create test data
        CalculationResult result1 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                100,
                10,
                5.5
        );
        
        CalculationResult result2 = new CalculationResult(
                ConvexHullAlgorithmType.GIFT_WRAPPING,
                200,
                15,
                8.2
        );
        
        CalculationResult result3 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                300,
                20,
                7.1
        );
        
        // Save test data
        calculationResultRepository.save(result1);
        calculationResultRepository.save(result2);
        calculationResultRepository.save(result3);
        
        // Find by algorithm type
        List<CalculationResult> grahamResults = calculationResultRepository.findByAlgorithmType(
                ConvexHullAlgorithmType.GRAHAM_SCAN.name()
        );
        
        List<CalculationResult> giftWrappingResults = calculationResultRepository.findByAlgorithmType(
                ConvexHullAlgorithmType.GIFT_WRAPPING.name()
        );
        
        // Verify results
        assertEquals(2, grahamResults.size());
        assertEquals(1, giftWrappingResults.size());
    }
    
    @Test
    public void testCalculateAverageCalculationTime() {
        // Create test data
        CalculationResult result1 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                100,
                10,
                5.0
        );
        
        CalculationResult result2 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                200,
                15,
                15.0
        );
        
        // Save test data
        calculationResultRepository.save(result1);
        calculationResultRepository.save(result2);
        
        // Calculate average calculation time
        Double averageTime = calculationResultRepository.calculateAverageCalculationTimeByAlgorithmType(
                ConvexHullAlgorithmType.GRAHAM_SCAN.name()
        );
        
        // Verify result
        assertEquals(10.0, averageTime, 0.001);
    }
} 