package com.praj.resumeai.modules.resume.dto;

import lombok.Data;
import java.util.List;

@Data
public class ParsedResumeDTO {
    private String fullName;
    private String email;
    private String phone;
    private String summary;
    private List<String> skills;
    private List<Education> education;
    private List<Experience> experience;
    private List<Project> projects;

    @Data
    public static class Education {
        private String institution;
        private String degree;
        private String year;
    }

    @Data
    public static class Experience {
        private String company;
        private String role;
        private String duration;
        private String description;
    }

    @Data
    public static class Project {
        private String title;
        private String description;
        private List<String> technologies;
    }
}