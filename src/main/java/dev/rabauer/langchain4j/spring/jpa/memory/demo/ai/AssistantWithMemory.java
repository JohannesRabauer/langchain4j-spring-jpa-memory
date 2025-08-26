package dev.rabauer.langchain4j.spring.jpa.memory.demo.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

/**
 * Contract for an AI assistant that supports per-session chat memory.
 */
public interface AssistantWithMemory {

    /**
     * Sends a user message to the assistant within a memory-scoped conversation.
     *
     * @param memoryId   the identifier used to retrieve and persist conversation context
     * @param userMessage the text message from the user
     * @return the assistant's textual response
     */
    String chat(@MemoryId long memoryId, @UserMessage String userMessage);
}
