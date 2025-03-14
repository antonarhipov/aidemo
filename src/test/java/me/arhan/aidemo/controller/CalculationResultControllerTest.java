package me.arhan.aidemo.controller;

import me.arhan.aidemo.dto.CalculationResultDto;
import me.arhan.aidemo.entity.CalculationResult;
import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import me.arhan.aidemo.service.ConvexHullService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CalculationResultControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConvexHullService convexHullService;

    @InjectMocks
    private CalculationResultController calculationResultController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(calculationResultController).build();
    }

    @Test
    public void testGetAllResults() throws Exception {
        // Create test data
        CalculationResult result1 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                100,
                10,
                5.5
        );
        result1.setId(1L);
        result1.setCreatedAt(LocalDateTime.now());

        CalculationResult result2 = new CalculationResult(
                ConvexHullAlgorithmType.GIFT_WRAPPING,
                200,
                15,
                8.2
        );
        result2.setId(2L);
        result2.setCreatedAt(LocalDateTime.now());

        List<CalculationResult> results = Arrays.asList(result1, result2);

        // Mock service
        when(convexHullService.getAllCalculationResults()).thenReturn(results);

        // Perform request and verify response
        mockMvc.perform(get("/api/results")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].algorithmType", is("GRAHAM_SCAN")))
                .andExpect(jsonPath("$[0].inputPointsCount", is(100)))
                .andExpect(jsonPath("$[0].hullPointsCount", is(10)))
                .andExpect(jsonPath("$[0].calculationTimeMs", closeTo(5.5, 0.001)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].algorithmType", is("GIFT_WRAPPING")))
                .andExpect(jsonPath("$[1].inputPointsCount", is(200)))
                .andExpect(jsonPath("$[1].hullPointsCount", is(15)))
                .andExpect(jsonPath("$[1].calculationTimeMs", closeTo(8.2, 0.001)));

        verify(convexHullService, times(1)).getAllCalculationResults();
    }

    @Test
    public void testGetResultsByAlgorithm() throws Exception {
        // Create test data
        CalculationResult result1 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                100,
                10,
                5.5
        );
        result1.setId(1L);
        result1.setCreatedAt(LocalDateTime.now());

        CalculationResult result2 = new CalculationResult(
                ConvexHullAlgorithmType.GRAHAM_SCAN,
                200,
                15,
                8.2
        );
        result2.setId(2L);
        result2.setCreatedAt(LocalDateTime.now());

        List<CalculationResult> results = Arrays.asList(result1, result2);

        // Mock service
        when(convexHullService.getCalculationResultsByAlgorithmType(ConvexHullAlgorithmType.GRAHAM_SCAN))
                .thenReturn(results);

        // Perform request and verify response
        mockMvc.perform(get("/api/results/algorithm/GRAHAM_SCAN")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].algorithmType", is("GRAHAM_SCAN")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].algorithmType", is("GRAHAM_SCAN")));

        verify(convexHullService, times(1))
                .getCalculationResultsByAlgorithmType(ConvexHullAlgorithmType.GRAHAM_SCAN);
    }

    @Test
    public void testGetResultsByAlgorithm_InvalidAlgorithm() throws Exception {
        // Perform request and verify response
        mockMvc.perform(get("/api/results/algorithm/INVALID_ALGORITHM")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAverageCalculationTimes() throws Exception {
        // Mock service
        when(convexHullService.calculateAverageCalculationTime(ConvexHullAlgorithmType.GRAHAM_SCAN))
                .thenReturn(5.5);
        when(convexHullService.calculateAverageCalculationTime(ConvexHullAlgorithmType.GIFT_WRAPPING))
                .thenReturn(8.2);

        // Perform request and verify response
        mockMvc.perform(get("/api/results/stats/average-times")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.GRAHAM_SCAN", closeTo(5.5, 0.001)))
                .andExpect(jsonPath("$.GIFT_WRAPPING", closeTo(8.2, 0.001)));

        verify(convexHullService, times(1))
                .calculateAverageCalculationTime(ConvexHullAlgorithmType.GRAHAM_SCAN);
        verify(convexHullService, times(1))
                .calculateAverageCalculationTime(ConvexHullAlgorithmType.GIFT_WRAPPING);
    }
} 
