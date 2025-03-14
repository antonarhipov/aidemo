package me.arhan.aidemo.web;

import me.arhan.aidemo.math.ConvexHullAlgorithmType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the visualization UI.
 */
@Controller
public class VisualizationController {

    /**
     * Displays the visualization UI.
     *
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping("/")
    public String visualize(Model model) {
        model.addAttribute("algorithms", ConvexHullAlgorithmType.values());
        return "visualize";
    }
} 