package me.arhan.aidemo;

import me.arhan.aidemo.math.D;
import me.arhan.aidemo.math.TheAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlgorithmController {

    @PostMapping("/ds")
    public ResponseEntity<List<D>> createDList(@RequestBody List<D> ds) {
        D[] input = ds.toArray(new D[0]);
        List<D> result = TheAlgorithm.run(input);
        System.out.println("The convex contains " + result.size() + " points");
        return ResponseEntity.ok(result);
    }

}
