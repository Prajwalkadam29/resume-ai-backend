package com.praj.resumeai.modules.matching.controllers;

import com.praj.resumeai.common.dto.ApiResponse;
import com.praj.resumeai.modules.matching.dto.MatchResultDTO;
import com.praj.resumeai.modules.matching.dto.RecommendationDTO;
import com.praj.resumeai.modules.matching.services.MatchingService;
import com.praj.resumeai.modules.matching.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final RecommendationService recommendationService;

    @GetMapping("/analyze")
    public ResponseEntity<ApiResponse<MatchResultDTO>> analyzeGap(
            @RequestParam String resumeId,
            @RequestParam Long jobRoleId) {
        try {
            // 1. Calculate the basic match
            MatchResultDTO result = matchingService.calculateMatch(resumeId, jobRoleId);

            // 2. If there are missing skills, get AI-powered recommendations
            if (!result.getMissingSkills().isEmpty()) {
                List<RecommendationDTO> recs = recommendationService.getRecommendations(result.getMissingSkills());
                result.setRecommendations(recs);
            }

            return ResponseEntity.ok(new ApiResponse<>("Deep analysis and learning path generated", true, result, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(e.getMessage(), false, null, null));
        }
    }
}