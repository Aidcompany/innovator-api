package com.innovator.innovator.controllers;

import com.innovator.innovator.models.Recommendation;
import com.innovator.innovator.models.User;
import com.innovator.innovator.services.RecommendationService;
import com.innovator.innovator.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RecommendationController {

    private UserService userService;
    private RecommendationService recommendationService;

    @PostMapping("/send_recommendation/{clientId}")
    public ResponseEntity<Map<String, String>> sendRecommendation(@PathVariable int clientId, @RequestBody Recommendation recommendationBody) {
        User user = userService.findById(clientId).get();
        Recommendation recommendation = new Recommendation();

        recommendation.setMessageText(recommendationBody.getMessageText());
        recommendation.setCustomEmail(recommendationBody.getCustomEmail());
        recommendation.setEmail(recommendationBody.getEmail());
        recommendation.setUser(user);

        recommendationService.saveRecommendation(recommendation);

        return ResponseEntity.ok(Map.of("message", "Message sended"));
    }
}
