package com.praj.resumeai.modules.skills.controllers;

import com.praj.resumeai.common.dto.ApiResponse;
import com.praj.resumeai.modules.skills.entities.UserSkillProgress;
import com.praj.resumeai.modules.skills.services.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<UserSkillProgress>> updateProgress(
            @RequestParam String userId,
            @RequestParam String skillName,
            @RequestParam UserSkillProgress.ProgressStatus status) {

        UserSkillProgress updated = progressService.updateStatus(userId, skillName, status);
        return ResponseEntity.ok(new ApiResponse<>("Progress updated successfully", true, updated, null));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<UserSkillProgress>>> getProgress(@PathVariable String userId) {
        List<UserSkillProgress> progressList = progressService.getUserProgress(userId);
        return ResponseEntity.ok(new ApiResponse<>("User progress retrieved", true, progressList, null));
    }
}