package com.of.ll.adapter.out.ai.prompt;

import com.of.ll.domain.model.Context;

public final class PromptBuilder {

    private PromptBuilder() {
    }

    public static String build(final Context context) {

        final StringBuilder sb = new StringBuilder();

        sb.append("""
                Vrať výhradně JSON bez jakéhokoli dalšího textu.
                Vrať maximálně 5 aktivit.
                
                Formát:
                {
                  "activities": [
                    {
                      "title": "...",
                      "type": "OUTDOOR | DIY",
                      "age_range": "6-10",
                      "duration_min": 30,
                      "why_today": "...",
                      "description": "...",
                      "steps": ["...", "..."],
                      "materials": [],
                      "safety_notes": "..."
                    }
                  ]
                }
                
                Kontext:
                """);

        sb.append("- Počasí: ")
                .append(context.weather())
                .append("\n");

        sb.append("- Věk: ")
                .append(context.ageRange())
                .append("\n");

        sb.append("- Čas: ")
                .append(context.availableTime().minutes())
                .append(" minut\n");

        if (!context.excludeTitles().isEmpty()) {
            sb.append("\nNeopakuj tyto aktivity:\n");
            context.excludeTitles()
                    .forEach(t -> sb.append("- ").append(t).append("\n"));
        }

        if (context.regenerateSeed() != null) {
            sb.append("\nPoužij náhodné semeno: ")
                    .append(context.regenerateSeed())
                    .append("\n");
        }

        return sb.toString();
    }
}
