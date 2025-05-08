package io.lacrobate.tiago.adapter.ia;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lacrobate.tiago.domain.AiExceptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("eventExtractor")
@Slf4j
public class OpenAiModelAdapter implements AiModelPort {
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    private static final String SYSTEM_PROMPT = """
        Tu es un assistant spécialisé dans l'extraction d'informations structurées.
        Analyse le texte fourni et extrait les informations pertinentes pour créer un événement.
        
        Retourne UNIQUEMENT un objet JSON valide avec la structure suivante :
        {
          "titre": "Titre de l'événement",
          "startDate": "Date et heure de début (format ISO 8601)",
          "endDate": "Date et heure de fin (format ISO 8601)",
          "description": "Description de l'événement"
        }
        
        Règles importantes:
        1. Comprends le contexte temporel (demain, ce soir, etc.) par rapport à la date actuelle
        2. Utilise toujours le format ISO 8601 pour les dates (YYYY-MM-DDThh:mm:ss)
        3. Inclus tous les champs, même si certaines informations doivent être déduites
        4. N'inclus pas d'explications ou de texte supplémentaire, uniquement le JSON pur
        
        Exemple:
        - Entrée: "ajouter l'evenement Anniversaire de Sara, de 15h à 16h demain."
        - Sortie: {"titre":"Anniversaire de Sara","startDate":"2025-05-08T15:00:00","endDate":"2025-05-08T16:00:00","description":"Anniversaire de Sara"}
        """;

    public OpenAiModelAdapter(ChatClient chatClient, ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }


    @Override
    public EventData processQuery(String query) {
        SystemMessage systemMessage = new SystemMessage(SYSTEM_PROMPT);
        UserMessage userMessage = new UserMessage(query);

        List<Message> messages = List.of(systemMessage, userMessage);
        Prompt prompt = new Prompt(messages);

        ChatResponse response = chatClient.call(prompt);

        return processJson(response);
    }

    private EventData processJson(ChatResponse response) {
        String jsonContent = response.getResult().getOutput().getContent().trim();

        // Nettoyer la réponse au cas où le modèle ajoute des backticks ou des marqueurs de code
        jsonContent = cleanJsonResponse(jsonContent);

        try {
            // Valider que c'est un JSON valide en le désérialisant (optional)
            EventData eventData = objectMapper.readValue(jsonContent, EventData.class);
            return eventData;
        } catch (Exception e) {
            log.error("{\"error\":\"Impossible de parser la réponse en JSON valide\"}");
            throw new AiExceptionException(e);
        }
    }

    private String cleanJsonResponse(String response) {
        // Supprimer les backticks de markdown et les identifiants json/JSON si présents
        response = response.replaceAll("```json\\s*", "").replaceAll("```JSON\\s*", "").replaceAll("```\\s*", "");
        return response;
    }
}