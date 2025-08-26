package dev.rabauer.langchain4j.spring.jpa.memory.demo;

import dev.rabauer.langchain4j.spring.jpa.memory.demo.ai.AiService;
import dev.rabauer.langchain4j.spring.jpa.memory.demo.ai.AssistantWithMemory;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes an endpoint to process chat messages using
 * an assistant with conversation memory.
 */
@RestController
@RequestMapping("/memory")
public class MemoryController {

    private final AssistantWithMemory aiAssistant;

    /**
     * Constructs the controller and initializes the assistant instance.
     *
     * @param aiService service used to create the assistant with memory support
     */
    public MemoryController(AiService aiService) {
        this.aiAssistant = aiService.createChatModelWithMemory();
    }

    /**
     * Processes a text message in the context of a specific memory identifier.
     *
     * @param request the payload containing the memoryId and the text message
     * @return the assistant's response as plain text
     */
    @PostMapping("/process")
    public String processMessage(@RequestBody MemoryRequest request) {
        return aiAssistant
                .chat(request.memoryId, request.textMessage);
    }

    /**
     * Request payload for processing a message with a conversation memory.
     *
     * @param memoryId    the identifier used to store/retrieve conversation state
     * @param textMessage the user-provided message to process
     */
    public record MemoryRequest(long memoryId, String textMessage) {
    }
}
