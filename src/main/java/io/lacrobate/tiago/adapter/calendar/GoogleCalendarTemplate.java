package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static io.lacrobate.tiago.adapter.calendar.GoogleOauthTemplate.DEFAULT_USER_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleCalendarTemplate {

    private final GoogleOauthTemplate template;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google.application.name}")
    private String applicationName;

    private final LocalServerReceiver receiver =
            new LocalServerReceiver.Builder().setPort(8888).setCallbackPath("/Callback").build();
    private GoogleAuthorizationCodeFlow flow;

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

    public String storeCredentials(AuthorizationRequestUrl requestUrl) {
        try {
            // receive authorization code and exchange it for an access token
            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code)
                    .setRedirectUri(requestUrl.getRedirectUri())
                    .execute();
            // store credential and return it
            Credential credential = flow.createAndStoreCredential(response, DEFAULT_USER_ID);

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

    public AuthorizationCodeRequestUrl authRequestUrl() throws IOException, GeneralSecurityException {
        // Supprimez les tokens existants pour forcer une nouvelle autorisation
        template.clearStoredCredentials();

        String redirectUri = receiver.getRedirectUri();
        // open in browser
        this.flow = template.defaultFlow();
		return flow.newAuthorizationUrl().setRedirectUri(redirectUri);
    }

    /**
     * Vérifie l'état actuel de l'autorisation
     */
    public AuthStatus checkAuthorizationStatus() {
        try {
            Credential credential = template.currentUserCredentials();
            
            if (credential != null && credential.getAccessToken() != null) {
                // Vérifie si le token est expiré
                if (credential.getExpiresInSeconds() == null || credential.getExpiresInSeconds() <= 0) {
                    return AuthStatus.CODE1;
                }
                log.info("{} - token expire dans {} secondes", AuthStatus.CODE2, credential.getExpiresInSeconds());
                return AuthStatus.CODE2;
            } else {
                return AuthStatus.CODE3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("{} - message : {} ",AuthStatus.CODE4, e.getMessage());
            return AuthStatus.CODE4;
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

    /**
     * Crée un client pour l'API Google Calendar
     */
    public Event insertEvent(Event event) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials();
        Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials).setApplicationName(
                applicationName).build();
        return calendar.events().insert("primary", event).execute();
    }
}

