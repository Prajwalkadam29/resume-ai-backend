package com.praj.resumeai.modules.skills.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "job_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_role_skills",
            joinColumns = @JoinColumn(name = "job_role_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> requiredSkills;
}