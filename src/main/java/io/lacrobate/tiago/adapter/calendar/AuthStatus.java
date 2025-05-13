package io.lacrobate.tiago.adapter.calendar;

import lombok.Getter;

@Getter
public enum AuthStatus {
    CODE1("Le token d'accès est expiré. Veuillez vous réautoriser."), 
    CODE2("Autorisé! Token d'accès valide"),
    CODE3("Non autorisé. Veuillez vous connecter à Google Calendar."), 
    CODE4("Erreur lors de la vérification de l'autorisation");
    private final String message;
    
    AuthStatus(String message) {
        this.message = message;
    }
}
