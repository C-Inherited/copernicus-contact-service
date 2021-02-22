package com.copernicus.contactservice.client;

import com.copernicus.contactservice.controller.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("account-service")
public interface AccountClient {

    @GetMapping("/account/{id}")
    public AccountDTO getAccount(@PathVariable Integer id);


}
