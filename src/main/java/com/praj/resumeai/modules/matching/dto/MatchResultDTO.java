package com.praj.resumeai.modules.matching.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MatchResultDTO {
    private String jobTitle;
    private double matchPercentage;
    private List<String> matchedSkills;
    private List<String> missingSkills;
}