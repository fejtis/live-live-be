# Architektura aplikace
1. Účel aplikace
- Denní návrh aktivit pro rodiny
- Backend je autorita, AI je návrhový nástroj
2. High-level architektura
- Modulární monolit
- Hexagonální architektura
- REST API
- AI jako nedůvěryhodný vstup Hlavní use-case
3.  Hlavní use-case
- Generate daily activities
4. Tok requestu
- REST → UseCase → AI → Pipeline → Scoring → Fallback → Response
5. Zásady
- žádná logika v controllerech
- žádné opravy AI textu
- fallback je povinný
- doména bez Springu