package com.copernicus.contactservice.client;

import com.copernicus.contactservice.controller.dto.AccountDTO;
import com.copernicus.contactservice.controller.dto.AuthenticationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/account/{id}")
    AccountDTO getAccount(@PathVariable Integer id, @RequestHeader(value = "Authorization") String authorizationHeader);

    @PostMapping("account/authenticate")
    ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest);

}
