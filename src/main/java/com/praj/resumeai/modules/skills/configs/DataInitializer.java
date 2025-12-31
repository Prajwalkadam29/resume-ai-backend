package com.praj.resumeai.modules.skills.configs;

import com.praj.resumeai.modules.skills.entities.JobRole;
import com.praj.resumeai.modules.skills.entities.Skill;
import com.praj.resumeai.modules.skills.repositories.JobRoleRepository;
import com.praj.resumeai.modules.skills.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SkillRepository skillRepository;
    private final JobRoleRepository jobRoleRepository;

    @Override
    public void run(String... args) {
        if (skillRepository.count() == 0) {
            // 1. Create Skills
            Set<Skill> javaSkills = Stream.of("Java", "Spring Boot", "PostgreSQL", "Docker", "Redis", "Kafka")
                    .map(name -> skillRepository.save(Skill.builder().name(name).category("Backend").build()))
                    .collect(Collectors.toSet());

            // 2. Create a Job Role
            jobRoleRepository.save(JobRole.builder()
                    .title("Java Backend Developer")
                    .requiredSkills(javaSkills)
                    .build());

            System.out.println("PostgreSQL Data Initialized: Java Backend Developer role created.");
        }
    }
}