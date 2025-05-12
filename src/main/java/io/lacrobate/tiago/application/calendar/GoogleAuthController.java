package io.lacrobate.tiago.application.calendar;

import io.lacrobate.tiago.adapter.calendar.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/googleauth")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping("/authorize")
    public String authorize() {
        return googleAuthService.initiateAuthorization();
    }
    
    @GetMapping("/status")
    public String getAuthStatus() {
        return googleAuthService.checkAuthorizationStatus();
    }
    
    @GetMapping("/clear")
    public String clearTokens() {
        return googleAuthService.clearStoredCredentials();
    }
}