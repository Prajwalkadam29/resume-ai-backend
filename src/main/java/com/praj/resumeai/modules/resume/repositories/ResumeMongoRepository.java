package com.praj.resumeai.modules.resume.repositories;

import com.praj.resumeai.modules.resume.entities.ResumeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeMongoRepository extends MongoRepository<ResumeEntity, String> {
}