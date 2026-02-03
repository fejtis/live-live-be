package com.of.ll.adapter.out.ai;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.of.ll.adapter.out.ai.mapper.AiActivityMapper;
import com.of.ll.domain.model.Activity;
import com.of.ll.domain.model.Context;
import com.of.ll.port.out.ActivityGeneratorPort;

@Component
public class SpringAiActivityGenerator implements ActivityGeneratorPort {

    private final ChatClient chatClient;
    private final Resource prompt = new ClassPathResource("prompt/activities.st");
    private final AiActivityMapper aiActivityMapper;
    private final Logger log = LoggerFactory.getLogger(SpringAiActivityGenerator.class);

    public SpringAiActivityGenerator(final ChatClient chatClient, final AiActivityMapper aiActivityMapper) {
        this.chatClient = chatClient;
        this.aiActivityMapper = aiActivityMapper;
    }

    @Override
    public List<Activity> generate(final Context context) {
        try {
            final String json = chatClient
                    .prompt()
                    .user(promptUserSpec -> promptUserSpec.text(prompt)
                            .param("weather", context.weather())
                            .param("age", context.ageRange())
                            .param("time", context.availableTime()))
                    .call().content();

            if (json != null) {
                return aiActivityMapper.parse(json).activities().stream().map(aiActivityMapper::toDomain)
                        .flatMap(Optional::stream)
                        .toList();
            }
        } catch (final Exception e) {
            // anti-corruption pattern: wrap exceptions from external systems
            log.warn("AI activity generation failed. Fallback wll be used. Reason={}", e.getClass().getSimpleName());
            log.debug("AI generation exception detail", e);
        }

        return List.of();
    }
}
