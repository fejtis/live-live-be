# Zásada roadmapy

```
Každý commit musí dávat smysl sám o sobě.
```
Žádné „rozjeté refactory“.

Žádné „ještě to dodělám“.

## 1️⃣ Commit 1 – Architektonický skelet

Cíl: projekt má tvar.
balíčky:
- api
- application
- domain
- port
- adapter
- config

ARCHITECTURE.md

DECISIONS.md

✅ build projde
✅ žádná logika

2️⃣ Commit 2 – Doménové modely

Cíl: doména existuje.

Activity

Context

AgeRange

Duration

enumy

✅ bez Springu
✅ jednotkové testy domény
❌ žádné adaptery

3️⃣ Commit 3 – Filtry + pipeline

Cíl: umíš vyřazovat.

ActivityFilter (interface)

Safety / Age / Time / Weather

Pipeline

✅ testy pro každý filtr
✅ test pipeline jako celek

4️⃣ Commit 4 – Scoring + selector

Cíl: umíš vybírat TOP 3.

ScoringPolicy

TopActivitiesSelector

✅ deterministické testy
✅ žádné náhody

5️⃣ Commit 5 – Use-case (application layer)

Cíl: celý scénář funguje bez REST.

GenerateDailyActivitiesUseCase

fake ActivityGenerator

fake TelemetryPort

✅ test „happy path“
✅ test fallback path

6️⃣ Commit 6 – Fallback adapter

Cíl: systém nikdy nevrátí prázdno.

StaticActivityGenerator

fallback data

✅ fallback prochází pipeline
✅ označení source = FALLBACK

7️⃣ Commit 7 – REST adapter

Cíl: FE se může připojit.

Controller

DTO

Mappers

✅ žádná logika
✅ 400 pro nevalidní vstup

8️⃣ Commit 8 – AI adapter (Gemini)

Cíl: živá AI, ale pod kontrolou.

timeout

retry (max 1)

JSON parsing

ACL

✅ AI může selhat
✅ BE se nezhroutí

9️⃣ Commit 9 – Telemetrie

Cíl: víš, co se děje.

domain events

Micrometer adapter

✅ žádný vliv na flow

🔟 Commit 10 – Cleanup & guardrails

Cíl: připraveno na růst.

package visibility

final classes

README update

TODO pro future

Co nedělat v průběhu

nepřeskakuj kroky

nepřidávej DB

nepřidávej auth

nepřidávej caching

Kdy poznáš, že to děláš správně

testy jsou rychlé

změna AI nerozbije doménu

FE je hloupé

fallback tě uklidňuje