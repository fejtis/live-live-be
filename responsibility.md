# Odpovědnosti 
## api.rest
- Jediný vstup z FE
- Validace vstupu
- Mapování DTO → domain
- ŽÁDNÁ logika
## application
- Orchestrace use-casu
- Žádná technická závislost
- Volá doménu + porty
- neobsahuje technické detaily
- ví v jakém pořadí se co má stát

👉 „dirigent, ne muzikant“

### Use-case: Generate daily activities
1. přijmi Context
2. zavolej ActivityGeneratorPort (AI)
3. pokud fail → fallback
4. spusť filter pipeline
5. pokud 0 → fallback + pipeline
6. score + vyber TOP 3
7. publish domain event
8. vrať výsledek


## domain
**Srdce aplikace**

- Neví nic o Springu
- Neví nic o AI
- Neví nic o JSONu
- Obsahuje:
  - pravidla
  - filtry
  - scoring
  - výběr TOP 3

👉 Tohle je to, co bys mohl zítra použít v CLI / batchi / jiné appce

## port
Kontrakty:
- co aplikace potřebuje
- ne jak se to dělá

## adapter
- REST (vstupní adapter)
  - Odpovědnosti
    - přijmout HTTP request
    - validovat syntaxi
    - namapovat DTO -> Context
    - zavolat use-case
    - namapovat výsledek -> response DTO
- AI (výstupní adapter)
  - volání externí služby
  - timeouts
  - retry (max velmi mírně)
  - mapování RAW JSON -> AiActivityDto
- telemetry (výstupní adapter)
  - poslouchá doménové události
  - loguje / posílá metriky
  - neovlivňuje tok
  - pokud spadne, nic jiného se nesmí stát
- fallback (výstupní adapter)
  - vrací předdefinované aktivity
  - implementuje stejný port jako AI
  - žádné podmínky
  - = **Null Object pattern**

👉 Všechno, co se může rozbít, patří sem

## config
- Wiring
- profily
- feature toggles

# Ověření architektury
**Toto nesmí nikdy nastat:**
- ❌ Controller volá AI přímo
- ❌ AI adapter vrací doménový objekt
- ❌ Mapper obsahuje business pravidla
- ❌ Fallback rozhoduje, kdy se použije
- ❌ ActivityService plný ifů
  ❌ filtry mění data
- ❌ filtry jsou závislé na Springu
- ❌ scoring jako filtr
- ❌ jeden filtr dělá víc věcí
