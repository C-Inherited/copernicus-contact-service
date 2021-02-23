package com.copernicus.contactservice.client;

import com.copernicus.contactservice.controller.dto.AccountDTO;
import com.copernicus.contactservice.controller.dto.AuthenticationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/account/{id}")
    AccountDTO getAccount(@PathVariable Integer id);

    @PostMapping( "account/authenticate")
    ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest);
}
