package dev.rabauer.langchain4j.spring.jpa.memory.demo.ai;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.rabauer.langchain4j.spring.jpa.memory.demo.persistence.ChatMessageEntity;
import dev.rabauer.langchain4j.spring.jpa.memory.demo.persistence.ChatMessageRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA-backed implementation of {@link ChatMemoryStore} that persists chat messages
 * and retrieves them by a provided memory identifier.
 */
@Component
public class JpaChatMemoryService implements ChatMemoryStore {

    private final ChatMessageRepository repository;

    /**
     * Creates a new JPA chat memory service.
     *
     * @param repository repository used to persist and read chat messages
     */
    public JpaChatMemoryService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads all messages for the given memory identifier in chronological order.
     *
     * @param memoryId the conversation memory identifier
     * @return a list of {@link ChatMessage} instances representing the conversation history
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        return repository.findByMemoryIdOrderByCreatedAtAsc(memoryId.toString())
                .stream()
                .map(entity -> ChatMessageDeserializer.messageFromJson(entity.getContent()))
                .toList();
    }

    /**
     * Persists the provided messages for the given memory identifier.
     *
     * @param memoryId the conversation memory identifier
     * @param messages the messages to persist
     */
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        messages.forEach(
                message ->
                {
                    ChatMessageEntity entity = new ChatMessageEntity();
                    entity.setMemoryId(memoryId.toString());
                    entity.setContent(ChatMessageSerializer.messageToJson(message));
                    entity.setCreatedAt(LocalDateTime.now());
                    repository.save(entity);
                }
        );
    }

    /**
     * Deletes all messages associated with the given memory identifier.
     *
     * @param memoryId the conversation memory identifier
     */
    @Override
    public void deleteMessages(Object memoryId) {
        this.repository.deleteByMemoryId(memoryId.toString());
    }
}
