package me.arhan.aidemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import me.arhan.aidemo.math.Point;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for the AlgorithmController.
 */
@ExtendWith(MockitoExtension.class)
public class AlgorithmControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ConvexHullService convexHullService;

    @InjectMocks
    private AlgorithmController algorithmController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(algorithmController).build();
    }

    @Test
    public void testCreateConvexHull() throws Exception {
        // Create test data
        List<Point> inputPoints = Arrays.asList(
                new Point(0, 0),
                new Point(1, 0),
                new Point(0, 1),
                new Point(1, 1)
        );

        List<Point> hullPoints = Arrays.asList(
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1)
        );

        // Mock service
        when(convexHullService.computeConvexHull(any())).thenReturn(hullPoints);

        // Perform request and verify response
        mockMvc.perform(post("/api/convexhull")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputPoints)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].x", is(0)))
                .andExpect(jsonPath("$[0].y", is(0)))
                .andExpect(jsonPath("$[1].x", is(1)))
                .andExpect(jsonPath("$[1].y", is(0)))
                .andExpect(jsonPath("$[2].x", is(1)))
                .andExpect(jsonPath("$[2].y", is(1)))
                .andExpect(jsonPath("$[3].x", is(0)))
                .andExpect(jsonPath("$[3].y", is(1)));

        verify(convexHullService, times(1)).computeConvexHull(any());
    }

    @Test
    public void testCreateConvexHullWithAlgorithm() throws Exception {
        // Create test data
        List<Point> inputPoints = Arrays.asList(
                new Point(0, 0),
                new Point(1, 0),
                new Point(0, 1),
                new Point(1, 1)
        );

        List<Point> hullPoints = Arrays.asList(
                new Point(0, 0),
                new Point(1, 0),
                new Point(1, 1),
                new Point(0, 1)
        );

        // Mock service
        when(convexHullService.computeConvexHull(any(), eq(ConvexHullAlgorithmType.GIFT_WRAPPING)))
                .thenReturn(hullPoints);

        // Perform request and verify response
        mockMvc.perform(post("/api/convexhull/GIFT_WRAPPING")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputPoints)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].x", is(0)))
                .andExpect(jsonPath("$[0].y", is(0)))
                .andExpect(jsonPath("$[1].x", is(1)))
                .andExpect(jsonPath("$[1].y", is(0)))
                .andExpect(jsonPath("$[2].x", is(1)))
                .andExpect(jsonPath("$[2].y", is(1)))
                .andExpect(jsonPath("$[3].x", is(0)))
                .andExpect(jsonPath("$[3].y", is(1)));

        verify(convexHullService, times(1))
                .computeConvexHull(any(), eq(ConvexHullAlgorithmType.GIFT_WRAPPING));
    }

    @Test
    public void testCreateConvexHullWithInvalidInput() throws Exception {
        // Mock service to throw exception
        when(convexHullService.computeConvexHull(any()))
                .thenThrow(new IllegalArgumentException("Input list cannot be null or empty"));

        // Perform request and verify response
        mockMvc.perform(post("/api/convexhull")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateConvexHullWithInvalidAlgorithm() throws Exception {
        // Create test data
        List<Point> inputPoints = Arrays.asList(
                new Point(0, 0),
                new Point(1, 0),
                new Point(0, 1),
                new Point(1, 1)
        );

        // Perform request and verify response
        mockMvc.perform(post("/api/convexhull/INVALID_ALGORITHM")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputPoints)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAvailableAlgorithms() throws Exception {
        // Perform request and verify response
        mockMvc.perform(get("/api/convexhull/algorithms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.GIFT_WRAPPING", is("Gift Wrapping (Jarvis March) algorithm")))
                .andExpect(jsonPath("$.GRAHAM_SCAN", is("Graham's Scan algorithm")));
    }
} 
