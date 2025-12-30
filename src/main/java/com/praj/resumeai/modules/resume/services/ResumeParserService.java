package com.praj.resumeai.modules.resume.services;

import com.praj.resumeai.modules.resume.dto.ParsedResumeDTO;
import com.praj.resumeai.modules.resume.entities.ResumeEntity;
import com.praj.resumeai.modules.resume.repositories.ResumeMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ResumeParserService {

    private final ChatClient chatClient;
    private final Tika tika;
    private final ResumeMongoRepository mongoRepository; // New dependency

    public ResumeParserService(ChatClient.Builder builder, ResumeMongoRepository mongoRepository) {
        this.tika = new Tika();
        this.mongoRepository = mongoRepository;
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public ResumeEntity parseAndSaveResume(MultipartFile file) {
        String rawText;

        try (InputStream stream = file.getInputStream()) {
            rawText = tika.parseToString(stream);
            if (rawText == null || rawText.trim().isEmpty()) {
                throw new RuntimeException("Empty content extracted.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Extraction failed: " + e.getMessage());
        }

        // 1. Get structured data from AI
        ParsedResumeDTO parsedData = chatClient.prompt()
                .system("You are a specialized ATS parser. Extract resume text into a strictly valid JSON object. DO NOT use markdown.")
                .user(u -> u.text("Resume Text:\n{text}").param("text", rawText))
                .call()
                .entity(ParsedResumeDTO.class);

        // 2. Wrap into Entity and Persist to MongoDB
        ResumeEntity entity = ResumeEntity.builder()
                .fileName(file.getOriginalFilename())
                .uploadTimestamp(LocalDateTime.now())
                .resumeData(parsedData)
                .build();

        return mongoRepository.save(entity);
    }
}