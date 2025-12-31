package com.praj.resumeai.modules.skills.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_skill_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkillProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // Temporary placeholder for Security Principal

    private String skillName;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    private LocalDateTime lastUpdated;

    public enum ProgressStatus {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }
}