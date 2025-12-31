package com.praj.resumeai.modules.skills.repositories;

import com.praj.resumeai.modules.skills.entities.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JobRoleRepository extends JpaRepository<JobRole, Long> {
    Optional<JobRole> findByTitleIgnoreCase(String title);
}