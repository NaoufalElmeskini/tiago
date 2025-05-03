package io.lacrobate.tiago.controller;

import io.lacrobate.tiago.adapter.GoogleOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class OAuthConsentController {

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @GetMapping("/authorize")
    public String authorize() {
        return googleOAuthService.initiateAuthorization();
    }
    
    @GetMapping("/status")
    public String getAuthStatus() {
        return googleOAuthService.checkAuthorizationStatus();
    }
    
    @GetMapping("/clear")
    public String clearTokens() {
        return googleOAuthService.clearStoredCredentials();
    }
}