package com.of.ll.adapter.out.ai.prompt;

import com.of.ll.domain.model.Context;

public final class PromptBuilder {

    private PromptBuilder() {
    }

    public static String build(final Context context) {
        final String seedPart = context.regenerateSeed() != null ? "\nPoužij náhodné semeno: " + context.regenerateSeed() : "";

        return """
                Vrať výhradně JSON bez jakéhokoli dalšího textu.
                
                Formát:
                {{
                  "activities": [
                    {{
                      "title": "...",
                      "type": "OUTDOOR | DIY",
                      "age_range": "6-10",
                      "duration_min": 30,
                      "why_today": "...",
                      "description": "...",
                      "steps": ["...", "..."],
                      "materials": [],
                      "safety_notes": "..."
                    }}
                  ]
                }}
                
                Kontext:
                - Počasí: {{weather}}
                - Věk: {{age}}
                - Čas: {{time}} minut
                %s
                """.formatted(seedPart);
    }

}
