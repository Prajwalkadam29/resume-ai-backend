package com.praj.resumeai.modules.resume.controllers;

import com.praj.resumeai.common.dto.ApiResponse;
import com.praj.resumeai.modules.resume.dto.ParsedResumeDTO;
import com.praj.resumeai.modules.resume.entities.ResumeEntity;
import com.praj.resumeai.modules.resume.services.ResumeParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeParserService resumeParserService;

    @PostMapping("/parse")
    public ResponseEntity<ApiResponse<ResumeEntity>> parseResume(@RequestParam("file") MultipartFile file) {
        try {
            ResumeEntity data = resumeParserService.parseAndSaveResume(file);
            return ResponseEntity.ok(new ApiResponse<>("Resume parsed and saved to Mongo", true, data, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(e.getMessage(), false, null, null));
        }
    }
}