package com.copernicus.contactservice.client;

import com.copernicus.contactservice.controller.dto.ValidationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient("validation-service")
public interface ValidationClient {

    @GetMapping("/email")
    boolean checkIsEmailValid(@RequestBody @Valid ValidationDTO validationDTO);

    @GetMapping("/name")
    boolean checkIsNameValid(@RequestBody @Valid ValidationDTO validationDTO);

    @GetMapping("/phone-number")
    boolean checkIsPhoneNumberValid(@RequestBody @Valid ValidationDTO validationDTO);

    @GetMapping("/country")
    boolean checkIsCountryValid(@RequestBody @Valid ValidationDTO validationDTO);
}
