package com.praj.resumeai.modules.skills.services;

import com.praj.resumeai.modules.skills.entities.UserSkillProgress;
import com.praj.resumeai.modules.skills.repositories.UserSkillProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserSkillProgressRepository progressRepository;

    public UserSkillProgress updateStatus(String userId, String skillName, UserSkillProgress.ProgressStatus status) {
        UserSkillProgress progress = progressRepository.findByUserIdAndSkillNameIgnoreCase(userId, skillName)
                .orElse(UserSkillProgress.builder()
                        .userId(userId)
                        .skillName(skillName)
                        .build());

        progress.setStatus(status);
        progress.setLastUpdated(LocalDateTime.now());

        return progressRepository.save(progress);
    }

    public List<UserSkillProgress> getUserProgress(String userId) {
        return progressRepository.findByUserId(userId);
    }
}