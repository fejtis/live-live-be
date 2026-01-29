Vrať výhradně JSON bez jakéhokoli dalšího textu.

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
- Počasí: {{weather}}
- Věk: {{age}}
- Čas: {{time}} minut