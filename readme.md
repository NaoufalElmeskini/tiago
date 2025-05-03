# Tiago : un secretaire de futur

(wip) Ce projet permet de gérer mes tâches du quotidien, notemment :
- ajouter des evenemnts à son calendrier Google Calendar à partir d'un message peu précis sur Telegram 
  - ex : "ajouter l'evenement 'Anniversaire de Sara' dans mon calndrier, demain de 18h à 20h"

## Prérequis

- Java 21
- Maven
- Compte Google

## Configuration des Credentials Google
[Configuration Google](gCalendar.readme)

## Structure du projet

```
root/
├── src/
│   └── main/
│       ├── java/
│       │   └── (...)
│       │         ├── (...)
│                 └── SpringApplication.java
│       └── resources/
│           └── application.yml
├── credentials/
│   └── credentials.json (à ajouter)
├── tokens/ (dossier pour stocker automatiquement les tokens OAuth)
└── pom.xml
```

## Démarrage

1. Clonez ce dépôt
2. Configurez les credentials Google comme expliqué ci-dessus
3. Lancez l'application :
   ```
   mvn spring-boot:run
   ```

## Points d'accès API
liste complete : [http-collection](http-collection)
- `GET /tiago-api/auth/authorize` : Initie le processus d'autorisation OAuth2
- `GET /tiago-api/auth/status` : Vérifie le statut de l'autorisation
- `GET /tiago-api/auth/clear` : Supprime les tokens stockés pour forcer une nouvelle autorisation
- `GET /tiago-api/calendar/add-birthday` : Ajoute l'événement d'anniversaire de Smael au calendrier Google