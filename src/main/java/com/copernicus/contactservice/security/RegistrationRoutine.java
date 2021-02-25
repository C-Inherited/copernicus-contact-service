package com.copernicus.contactservice.security;

import com.copernicus.contactservice.client.AccountClient;
import com.copernicus.contactservice.client.ValidationClient;
import com.copernicus.contactservice.controller.dto.AuthenticationRequest;
import com.copernicus.contactservice.controller.impl.ContactController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class RegistrationRoutine {

    @Autowired
    ValidationClient validationClient;

    @Autowired
    AccountClient accountClient;

    public static boolean isValidationRegistered = false;
    public static boolean isAccountRegistered = false;

    private static final Logger log = LoggerFactory.getLogger(RegistrationRoutine.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

    @Scheduled(fixedRate = 10000)
    public void checkRegistrationValidation() {
            if (!isValidationRegistered){
                CircuitBreaker circuitBreaker = circuitBreakerFactory.create("validation-service");
                log.info("Trying to register with validation-service {}", dateFormat.format(new Date()));
                AuthenticationRequest authenticationRequest = new AuthenticationRequest("contact-service", "contact-service");
                ResponseEntity<?> responseEntity= circuitBreaker.run(() -> validationClient.createAuthenticationToken(authenticationRequest), throwable -> fallbackTransaction("validation-service"));
                if (responseEntity != null) {
                    parseJWTValidation(responseEntity);
                    isValidationRegistered = true;
                    log.info("Registered with validation-service auth token: {}", ContactController.getContactAccountAuthOk());
                }
            }
    }

    @Scheduled(fixedRate = 10000)
    public void checkRegistrationAccount() {
            if (!isAccountRegistered){
                CircuitBreaker circuitBreaker = circuitBreakerFactory.create("account-service");
                log.info("Trying to register with account-service {}", dateFormat.format(new Date()));
                AuthenticationRequest authenticationRequest = new AuthenticationRequest("contact-service", "contact-service");
                ResponseEntity<?> responseEntity= circuitBreaker.run(() -> accountClient.createAuthenticationToken(authenticationRequest), throwable -> fallbackTransaction("account-service"));
                if (responseEntity != null) {
                    parseJWTAccount(responseEntity);
                    isAccountRegistered = true;
                    log.info("Registered with contact-service auth token: {}", ContactController.getContactAccountAuthOk());
                }
            }
    }

    private void parseJWTAccount(ResponseEntity<?> responseEntity) {
        String auth = Objects.requireNonNull(responseEntity.getBody()).toString();
        ContactController.setContactAccountAuthOk(auth.substring(5, auth.length() - 1));
    }

    private void parseJWTValidation(ResponseEntity<?> responseEntity) {
        String auth = Objects.requireNonNull(responseEntity.getBody()).toString();
        ContactController.setContactValidationAuthOk(auth.substring(5, auth.length() - 1));
    }

    private ResponseEntity<?> fallbackTransaction(String serviceName) {
        log.info( serviceName + " is not reachable {}", dateFormat.format(new Date()));
        return null;
    }
}
