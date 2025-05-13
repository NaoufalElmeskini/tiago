package io.lacrobate.tiago.adapter.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleOauthTemplate {
	private  static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	public static final String DEFAULT_USER_ID = "user";

	@Value("${google.tokens.folder.path}")
	private String tokensFolderPath;
	@Value("${google.credentials.folderpath}")
	private String credentialsFolderPath;
	@Value("${google.application.name}")
	private String applicationName;


	@Value("${google.oauth.callback}")
	private String oauthCallback;
	@Value("${google.credentials.filename}")
	private String CREDENTIAL_FILE_NAME;



	public GoogleAuthorizationCodeFlow flow(GoogleClientSecrets clientSecrets)
			throws IOException, GeneralSecurityException {
		return new GoogleAuthorizationCodeFlow.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensFolderPath)))
				.setAccessType("offline")
				.setApprovalPrompt("force")
				.build();
	}

	public GoogleAuthorizationCodeFlow defaultFlow()
			throws IOException, GeneralSecurityException {
		return flow(clientSecret());
	}

	public Credential currentUserCredentials() throws GeneralSecurityException, IOException {
		return defaultFlow().loadCredential(DEFAULT_USER_ID);
	}

	public GoogleClientSecrets clientSecret() throws IOException {
		return GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(new FileInputStream(Paths.get(credentialsFolderPath, CREDENTIAL_FILE_NAME).toFile())));
	}

	public LocalServerReceiver localServerReciever() {
		return new LocalServerReceiver.Builder().setPort(8888).setCallbackPath("/Callback")
				.build();
	}

	public Credential autorize(GoogleAuthorizationCodeFlow flow) throws IOException {
		LocalServerReceiver receiver = localServerReciever();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize(DEFAULT_USER_ID);
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
}
