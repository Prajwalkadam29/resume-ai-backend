package com.praj.resumeai.modules.skills.repositories;

import com.praj.resumeai.modules.skills.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByNameIgnoreCase(String name);
}