# 📋 Projet : Agent Personnel IA

Suivi du projet MVP basé sur Model - Contexte - Protocole.


## 🛠️ Progrès global

| Code    | Titre                       | Statut (🚧 En cours / ✅ Fait / ⏳ À faire)                                                   |
| :------ | :-------------------------- | :------------------------------------------------------------------------------------------ |
| MVP-001 | Initialiser projet AgentAPI | ✅ Fait                                                                                            |
| MVP-002 | Définir MCP v1              |                                                                                             |
| MVP-003 | Créer bot Telegram          | ✅ Fait ([@github/tiag-telegram-bot](https://github.com/NaoufalElmeskini/tiag-telegram-bot)) |
| MVP-004 | Réception/logs messages     |                                                                                             |
| MVP-005 | AgendaService               |                                                                                             |
| MVP-006 | Connexion Google Calendar   | ✅ Fait                                                                                            |
| MVP-007 | Ajouter événement agenda    | ✅ Fait                                                                                            |
| MVP-008 | Lister événements agenda    |                                                                                             |
| MVP-009 | Mapper intents agenda       |                                                                                             |
| MVP-010 | Service IA                  |                                                                                             |
| MVP-011 | Définir prompts IA          |                                                                                             |
| MVP-012 | Mapper intents IA           |                                                                                             |
| MVP-013 | Dispatcher vers services    |                                                                                             |

---
## 📅 Semaine 1 — Base technique

- [ ] **[MVP-001]** Initialiser le projet Spring Boot "AgentAPI".
- [ ] **[MVP-002]** Définir le schéma MCP v1 (Modèle, Contexte, Protocole).
- [ ] **[MVP-003]** Créer et connecter un bot Telegram.
- [ ] **[MVP-004]** Implémenter réception/logs des messages entrants.

## 📅 Semaine 2 — Connexion au calendrier personnel

- [ ] **[MVP-005]** Créer `AgendaService` (backend).
- [ ] **[MVP-006]** Connecter Google Calendar API via OAuth2.
- [ ] **[MVP-007]** Créer endpoint `POST /agenda/add-event`.
- [ ] **[MVP-008]** Créer endpoint `GET /agenda/list-today`.
- [ ] **[MVP-009]** Mapper intents Telegram vers actions calendrier.

## 📅 Semaine 3 — IA fact-checking & synthèse

- [ ] **[MVP-010]** Créer `IAService` pour appels API IA externes.
- [ ] **[MVP-011]** Définir format de prompts pour fact-checking et synthèse.
- [ ] **[MVP-012]** Mapper intents Telegram vers IAService.
- [ ] **[MVP-013]** Dispatcher selon l'intent détecté vers le bon service.

---

# 🧩 Règles
- Chaque tâche = une Issue GitHub.
- Chaque branche = `feature/MVP-XXX-titre`.
- Chaque PR lie son Issue.
- BDD recommandé pour description d'issue.
