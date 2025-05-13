package io.lacrobate.tiago.application.calendar;

import io.lacrobate.tiago.adapter.calendar.GoogleCalendarTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/googleauth")
public class GoogleAuthController {

    @Autowired
    private GoogleCalendarTemplate googleCalendarTemplate;

    @GetMapping("/authorize")
    public String authorize() {
        return googleCalendarTemplate.initiateAuthorization();
    }
    
    @GetMapping("/status")
    public String getAuthStatus() {
        return googleCalendarTemplate.checkAuthorizationStatus().getMessage();
    }
    
    @GetMapping("/clear")
    public String clearTokens() {
        return googleCalendarTemplate.clearStoredCredentials();
    }
}