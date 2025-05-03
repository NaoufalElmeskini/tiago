package io.lacrobate.tiago.adapter;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleOAuthService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String USER_ID = "user";

    @Value("${google.application.name}")
    private String applicationName;

    @Value("${google.credentials.folder.path}")
    private String credentialsFolderPath;

    @Value("${google.tokens.folder.path}")
    private String tokensFolderPath;

    @Value("${google.oauth.callback}")
    private String oauthCallback;

    /**
     * Initialise le processus d'autorisation OAuth2
     */
    public String initiateAuthorization() {
        try {
            // Supprimez les tokens existants pour forcer une nouvelle autorisation
            clearStoredCredentials();
            
            // Obtenez de nouvelles credentials
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            
            // Charge les secrets client depuis le fichier credentials.json
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(new FileInputStream(Paths.get(credentialsFolderPath, "credentials.json").toFile())));

            // Construit le flow et autorise les credentials
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensFolderPath)))
                    .setAccessType("offline")
                    .setApprovalPrompt("force")
                    .build();
                    
            LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                    .setPort(8888)
                    .setCallbackPath("/Callback")
                    .build();
                    
            Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize(USER_ID);
            
            if (credential != null && credential.getAccessToken() != null) {
                return "Autorisation réussie! Vous pouvez maintenant utiliser l'API Google Calendar.";
            } else {
                return "Échec de l'autorisation. Veuillez réessayer.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'autorisation: " + e.getMessage();
        }
    }
    
    /**
     * Vérifie l'état actuel de l'autorisation
     */
    public String checkAuthorizationStatus() {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            
            // Charge les secrets client
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(new FileInputStream(Paths.get(credentialsFolderPath, "credentials.json").toFile())));

            // Vérifie les credentials existantes
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensFolderPath)))
                    .build();
                    
            Credential credential = flow.loadCredential(USER_ID);
            
            if (credential != null && credential.getAccessToken() != null) {
                // Vérifie si le token est expiré
                if (credential.getExpiresInSeconds() == null || credential.getExpiresInSeconds() <= 0) {
                    return "Le token d'accès est expiré. Veuillez vous réautoriser.";
                }
                return "Autorisé! Token d'accès valide (expire dans " + credential.getExpiresInSeconds() + " secondes).";
            } else {
                return "Non autorisé. Veuillez vous connecter à Google Calendar.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la vérification de l'autorisation: " + e.getMessage();
        }
    }
    
    /**
     * Supprime les tokens stockés pour forcer une nouvelle autorisation
     */
    public String clearStoredCredentials() {
        try {
            File tokensDir = new File(tokensFolderPath);
            if (tokensDir.exists()) {
                File[] files = tokensDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            }
            return "Tokens supprimés avec succès. Une réautorisation sera nécessaire.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la suppression des tokens: " + e.getMessage();
        }
    }
    
    /**
     * Obtient les credentials pour l'API Google
     */
    public Credential getCredentials() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        
        // Charge les secrets client
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(Paths.get(credentialsFolderPath, "credentials.json").toFile())));

        // Vérifie les credentials existantes
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensFolderPath)))
                .setAccessType("offline")
                .build();
                
        Credential credential = flow.loadCredential(USER_ID);
        
        if (credential == null || credential.getAccessToken() == null) {
            throw new IOException("Aucune credential valide trouvée. Veuillez vous autoriser via /api/auth/authorize");
        }
        
        return credential;
    }
}