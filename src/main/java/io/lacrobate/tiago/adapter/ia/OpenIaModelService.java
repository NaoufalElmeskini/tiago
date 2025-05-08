package io.lacrobate.tiago.adapter.ia;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
//@ConditionalOnProperty(name = "ai.provider", havingValue = "openai")
@Qualifier("eventExtractor")
public class OpenIaModelService implements IaModelService {
    @Override
    public AiResponse processQuery(String message) {
        return null;
    }
}