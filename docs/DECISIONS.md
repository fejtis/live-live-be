# Rozhodnutí

1. proč REST a ne reaktivní
Reaktivní je fajn pro dlouhotrvající spojení,ale tady máme jasné requesty a ktrátké response 

2. proč hexagon
Hexagonální architektura nám umožňuje jasné oddělení domény od technických detailů, což zvyšuje testovatelnost a udržovatelnost kódu.
Např. se můžeme kdykoli rozhodnout pro změnu AI modelu nebo přidat nový bez zásahu do domény.

3. proč zatím bez DB
Zatím nemáme potřebu perzistence, vše je generováno na požádání. Pokud by se to změnilo, můžeme přidat DB vrstvu jako další adaptér.

4. proč fallback
Fallback je klíčový pro zajištění spolehlivosti aplikace. AI modely mohou selhat nebo vrátit nevhodné odpovědi, a fallback nám umožňuje zajistit, že uživatel vždy obdrží relevantní data.