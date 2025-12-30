package com.praj.resumeai.modules.resume.services;

import com.praj.resumeai.modules.resume.dto.ParsedResumeDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Slf4j
public class ResumeParserService {

    private final ChatClient chatClient;
    private final Tika tika;

    public ResumeParserService(ChatClient.Builder builder) {
        // Initialize Tika inside the constructor
        this.tika = new Tika();
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public ParsedResumeDTO parseResume(MultipartFile file) {
        String rawText;

        try (InputStream stream = file.getInputStream()) {
            log.info("Starting text extraction for file: {}", file.getOriginalFilename());

            // Extract text from the stream
            rawText = tika.parseToString(stream);

            if (rawText == null || rawText.trim().isEmpty()) {
                log.error("Extraction resulted in empty text for file: {}", file.getOriginalFilename());
                throw new RuntimeException("The uploaded PDF contains no readable text. It might be a scanned image.");
            }

            log.info("Successfully extracted {} characters", rawText.length());

        } catch (Exception e) {
            log.error("Tika extraction failed: {}", e.getMessage());
            throw new RuntimeException("Failed to process resume file: " + e.getMessage());
        }

        return chatClient.prompt()
                .system("""
                    You are a specialized ATS parser. 
                    Extract the resume text into a strictly valid JSON object.
                    Ensure the fields match the provided structure exactly.
                    DO NOT use markdown formatting or backticks in your response.
                    """)
                .user(u -> u.text("Resume Text to Parse:\n{text}").param("text", rawText))
                .call()
                .entity(ParsedResumeDTO.class);
    }
}