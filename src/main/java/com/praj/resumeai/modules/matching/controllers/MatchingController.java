package com.praj.resumeai.modules.matching.controllers;

import com.praj.resumeai.common.dto.ApiResponse;
import com.praj.resumeai.modules.matching.dto.MatchResultDTO;
import com.praj.resumeai.modules.matching.services.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/analyze")
    public ResponseEntity<ApiResponse<MatchResultDTO>> analyzeGap(
            @RequestParam String resumeId,
            @RequestParam Long jobRoleId) {
        try {
            MatchResultDTO result = matchingService.calculateMatch(resumeId, jobRoleId);
            return ResponseEntity.ok(new ApiResponse<>("Match analysis completed", true, result, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(e.getMessage(), false, null, null));
        }
    }
}