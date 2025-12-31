package com.praj.resumeai.modules.skills.repositories;

import com.praj.resumeai.modules.skills.entities.UserSkillProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserSkillProgressRepository extends JpaRepository<UserSkillProgress, Long> {
    List<UserSkillProgress> findByUserId(String userId);
    Optional<UserSkillProgress> findByUserIdAndSkillNameIgnoreCase(String userId, String skillName);
}