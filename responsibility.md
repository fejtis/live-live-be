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

👉 „dirigent, ne muzikant“

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
Špinavý svět:
- REST
- AI
- logging
- fallback data

👉 Všechno, co se může rozbít, patří sem

## config
- Wiring
- profily
- feature toggles