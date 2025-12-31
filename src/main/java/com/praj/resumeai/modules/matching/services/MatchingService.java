package com.praj.resumeai.modules.matching.services;

import com.praj.resumeai.modules.matching.dto.MatchResultDTO;
import com.praj.resumeai.modules.resume.entities.ResumeEntity;
import com.praj.resumeai.modules.resume.repositories.ResumeMongoRepository;
import com.praj.resumeai.modules.skills.entities.JobRole;
import com.praj.resumeai.modules.skills.entities.Skill;
import com.praj.resumeai.modules.skills.repositories.JobRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final ResumeMongoRepository resumeMongoRepository;
    private final JobRoleRepository jobRoleRepository;

    public MatchResultDTO calculateMatch(String resumeId, Long jobRoleId) {
        // 1. Fetch data from both databases
        ResumeEntity resume = resumeMongoRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() -> new RuntimeException("Job Role not found"));

        // 2. Normalize resume skills for matching (lowercase and trimmed)
        Set<String> resumeSkills = resume.getResumeData().getSkills().stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toSet());

        // 3. Compare with required skills from PostgreSQL
        List<String> matchedSkills = jobRole.getRequiredSkills().stream()
                .map(Skill::getName)
                .filter(skill -> resumeSkills.contains(skill.toLowerCase()))
                .toList();

        List<String> missingSkills = jobRole.getRequiredSkills().stream()
                .map(Skill::getName)
                .filter(skill -> !resumeSkills.contains(skill.toLowerCase()))
                .toList();

        // 4. Calculate Match Score using the formula:
        // Match Score = (Matched Skills / Total Required Skills) * 100
        double score = 0;
        if (!jobRole.getRequiredSkills().isEmpty()) {
            score = ((double) matchedSkills.size() / jobRole.getRequiredSkills().size()) * 100;
        }

        return MatchResultDTO.builder()
                .jobTitle(jobRole.getTitle())
                .matchPercentage(Math.round(score * 100.0) / 100.0) // Round to 2 decimal places
                .matchedSkills(matchedSkills)
                .missingSkills(missingSkills)
                .build();
    }
}