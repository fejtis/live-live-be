package com.of.ll.adapter.out.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiClientConfig {

    @Bean
    public ChatClient chatClient(final ChatClient.Builder builder) {
        return builder.build();

    }

}
