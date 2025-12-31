package com.praj.resumeai.modules.matching.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class RecommendationDTO {
    private String skillName;
    private String priority; // High, Medium, Low
    private String estimatedWeeks;
    private List<String> learningSteps;
    private String recommendedResource; // e.g., "Official Documentation", "Udemy", etc.
}