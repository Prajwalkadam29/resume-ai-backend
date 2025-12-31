package com.praj.resumeai.modules.matching.services;

import com.praj.resumeai.modules.matching.dto.RecommendationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecommendationService {

    private final ChatClient chatClient;

    public RecommendationService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public List<RecommendationDTO> getRecommendations(List<String> missingSkills) {
        if (missingSkills == null || missingSkills.isEmpty()) {
            return List.of();
        }

        log.info("Generating learning paths for: {}", missingSkills);

        return chatClient.prompt()
                .system("""
                    You are a career coach and technical architect. 
                    For the provided list of missing technical skills, generate a structured learning path.
                    Return a JSON array of objects, each containing:
                    - skillName
                    - priority (based on industry demand)
                    - estimatedWeeks
                    - learningSteps (a list of 3-4 specific topics to master)
                    - recommendedResource
                    DO NOT use markdown formatting.
                    """)
                .user(u -> u.text("Missing Skills: {skills}").param("skills", String.join(", ", missingSkills)))
                .call()
                .entity(new ParameterizedTypeReference<List<RecommendationDTO>>() {});
    }
}