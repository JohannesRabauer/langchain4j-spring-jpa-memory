package dev.rabauer.langchain4j.spring.jpa.memory.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, String> {
    List<ChatMessageEntity> findByMemoryIdOrderByCreatedAtAsc(String memoryId);

    List<ChatMessageEntity> deleteByMemoryId(String memoryId);
}
