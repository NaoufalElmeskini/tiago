# Intégration Google Calendar avec Spring Boot

Ce projet permet d'ajouter un événement statique "anniversaire Smael 28 mai 2025" à Google Calendar en utilisant Spring Boot.

## Prérequis

- Java 21
- Maven
- Compte Google

## Configuration des Credentials Google

Avant de pouvoir exécuter l'application, vous devez créer des credentials OAuth2 sur la console Google Cloud :

1. Accédez à [Google Cloud Console](https://console.cloud.google.com/)
2. Créez un nouveau projet
3. Activez l'API Google Calendar pour ce projet
4. Créez des identifiants OAuth 2.0
    - Type d'application : Application de bureau
5. Téléchargez le fichier JSON des credentials
6. Créez un dossier `credentials` à la racine du projet
7. Copiez le fichier JSON téléchargé dans ce dossier et renommez-le en `credentials.json`
8. Créez également un dossier `tokens` à la racine pour stocker les tokens d'accès

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
4. **Important - Nouvelle procédure d'authentification** :
    - Accédez d'abord à l'URL : `http://localhost:8080/api/auth/authorize`
      (Identifiants par défaut: admin/password)
    - Suivez le processus d'autorisation dans votre navigateur
    - Assurez-vous que le redirect_uri configuré dans la console Google Cloud est **exactement** : `http://localhost:8888/Callback`
    - Une fois autorisé, vous verrez un message de confirmation
5. Une fois correctement autorisé, accédez à l'endpoint pour créer l'événement d'anniversaire :
   ```
   http://localhost:8080/api/calendar/add-birthday
   ```

## Points d'accès API
liste complete : [http-collection](http-collection)
- `GET /tiago-api/auth/authorize` : Initie le processus d'autorisation OAuth2
- `GET /tiago-api/auth/status` : Vérifie le statut de l'autorisation
- `GET /tiago-api/auth/clear` : Supprime les tokens stockés pour forcer une nouvelle autorisation
- `GET /tiago-api/calendar/add-birthday` : Ajoute l'événement d'anniversaire de Smael au calendrier Google