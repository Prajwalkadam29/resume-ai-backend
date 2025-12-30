package com.praj.resumeai.modules.resume.entities;

import com.praj.resumeai.modules.resume.dto.ParsedResumeDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "parsed_resumes")
public class ResumeEntity {
    @Id
    private String id;
    private String fileName;
    private LocalDateTime uploadTimestamp;

    // This stores the entire structured JSON we got from Groq
    private ParsedResumeDTO resumeData;
}