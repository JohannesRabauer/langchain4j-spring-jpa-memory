package dev.rabauer.langchain4j.spring.jpa.memory.demo.ai;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service responsible for configuring and building an assistant that has per-user chat memory.
 * <p>
 * It wires an Ollama-backed chat model with a memory provider that persists and retrieves
 * conversation history using a {@link ChatMemoryStore}.
 * </p>
 */
@Service
public class AiService {

    private final ChatMemoryStore memoryStore;
    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;
    @Value("${ollama.model-name:llama3.2:1b}")
    private String ollamaModelName;

    /**
     * Constructs the AI service with a persistent chat memory store.
     *
     * @param memoryStore a {@link ChatMemoryStore} implementation backed by JPA
     */
    public AiService(JpaChatMemoryService memoryStore) {
        this.memoryStore = memoryStore;
    }

    /**
     * Creates an {@link AssistantWithMemory} instance configured with the Ollama chat model and
     * a {@link ChatMemoryProvider} that persists messages with a sliding window.
     *
     * @return a ready-to-use assistant that supports chat memory by memoryId
     */
    public AssistantWithMemory createChatModelWithMemory() {
        ChatModel model = OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaModelName)
                .build();

        AiServices<AssistantWithMemory> streamingChatModel = AiServices
                .builder(AssistantWithMemory.class)
                .chatModel(model);

        ChatMemoryProvider chatMemoryProvider = memoryId ->
                MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .chatMemoryStore(memoryStore)
                        .maxMessages(100)
                        .build();
        streamingChatModel.chatMemoryProvider(chatMemoryProvider);

        return streamingChatModel.build();
    }
}
