package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final GoogleOauthTemplate template;

    /**
     * Initialise le processus d'autorisation OAuth2
     */
    public String initiateAuthorization() {
        try {
            // Supprimez les tokens existants pour forcer une nouvelle autorisation
            template.clearStoredCredentials();
            GoogleAuthorizationCodeFlow flow = template.defaultFlow();
            Credential credential = template.autorize(flow);
            
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
            Credential credential = template.currentUserCredentials();
            
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
     * Obtient les credentials pour l'API Google
     */
    public Credential getCredentials() throws IOException, GeneralSecurityException {
        // Vérifie les credentials existantes
        Credential credential = template.currentUserCredentials();

        if (credential == null || credential.getAccessToken() == null) {
            throw new IOException("Aucune credential valide trouvée. Veuillez vous autoriser via /api/auth/authorize");
        }

        return credential;
    }

    public String clearStoredCredentials() {
        return template.clearStoredCredentials();
    }
}