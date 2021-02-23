package com.copernicus.contactservice.client;

import com.copernicus.contactservice.controller.dto.AuthenticationRequest;
import com.copernicus.contactservice.controller.dto.ValidationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient("validation-service")
public interface ValidationClient {

    @GetMapping("validation/email")
    boolean checkIsEmailValid(@RequestBody @Valid ValidationDTO validationDTO);

    @GetMapping("validation/name")
    boolean checkIsNameValid(@RequestBody @Valid ValidationDTO validationDTO);

    @GetMapping("validation/phone-number")
    boolean checkIsPhoneNumberValid(@RequestBody @Valid ValidationDTO validationDTO);

    @GetMapping("validation/country")
    boolean checkIsCountryValid(@RequestBody @Valid ValidationDTO validationDTO);

    @PostMapping( "validation/authenticate")
    ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest);

}
