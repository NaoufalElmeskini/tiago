# Tiago : Assistant Personnel Intelligent
![Status](https://img.shields.io/badge/status-work%20in%20progress-yellow)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)

Tiago est un assistant personnel intelligent qui facilite la gestion de vos tâches quotidiennes. Ce projet exploite les APIs modernes et l'IA pour automatiser des actions courantes à partir de simples commandes en langage naturel.

## 🚀 Fonctionnalités

- **Gestion de calendrier intelligente**
    - Ajouter des événements à Google Calendar à partir de messages en langage naturel sur Telegram
    - Exemple : _"Ajoute l'événement 'Anniversaire de Sara' dans mon calendrier, demain de 18h à 20h"_
- **Intégration d'IA** via OpenAI pour l'analyse des intentions utilisateur
- **Architecture hexagonale** pour améliorer l'évolutivité

## 🏗️ Architecture
Le projet suit une architecture hexagonale (ports & adapters) :
```
io.lacrobate.tiago/
├── adapter/                 # Adaptateurs (entrée/sortie)
│   ├── bot/                 # Adaptateur Telegram
│   ├── calendar/            # Adaptateurs pour Google Calendar
│   └── ia/                  # Adaptateur pour les services d'IA (OpenAI)
├── application/             # Services applicatifs
└── domain/                  # Modèle du domaine
│   ├── bot                  # Telegram (ports,...)
│   ├── calendar             # Calendar (ports,...)
│   └── ia/ports             # Services d'IA (ports,...)
```
## 📝 Configuration

### Variables d'environnement requises

| Variable          | Description                                               |
|-------------------|-----------------------------------------------------------|
| `OPENAI_API_KEY`  | **Obligatoire**. Clé API pour accéder aux services OpenAI |
| `bot_token`, `bot_username`, `bot_receiver` | **Obligatoire**. Token pour accéder aux services Telegram |
Obtenir un token pour la premiere fois : [Documentation](https://core.telegram.org/bots/api) 

### Configuration Google Calendar

1. Créez un projet dans la [Console Google Cloud](https://console.cloud.google.com/)
2. Activez l'API Google Calendar
3. Créez des identifiants OAuth2 (type Application Web)
4. Définissez l'URI de redirection autorisée : `http://localhost:8888/Callback`
5. Téléchargez le fichier JSON des credentials
6. Placez-le dans le dossier `credentials/credentials.json`

old : [Configuration Google](gCalendar.readme)


## Prérequis

- Java 21
- Maven
- Compte Google


## Points d'accès API
liste complete : [http-collection](http-collection)
- `GET /tiago-api/auth/authorize` : Initie le processus d'autorisation OAuth2
- `GET /tiago-api/auth/status` : Vérifie le statut de l'autorisation
- `GET /tiago-api/auth/clear` : Supprime les tokens stockés pour forcer une nouvelle autorisation
- `GET /tiago-api/calendar/add-event-demo` : Ajoute l'événement d'anniversaire de Smael au calendrier Google

## 🤝 Contribution
Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.

## 📄 Licence
Ce projet est sous licence MIT - voir le fichier LICENSE pour plus de détails.
