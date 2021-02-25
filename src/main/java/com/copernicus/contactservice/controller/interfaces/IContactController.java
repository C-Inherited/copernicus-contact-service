package com.copernicus.contactservice.controller.interfaces;

import com.copernicus.contactservice.controller.dto.ContactDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IContactController {

    ContactDTO getContact(Integer id, String authorizationHeader);

    List<ContactDTO> getAllContact(String authorizationHeader);

    ContactDTO postContact(ContactDTO contactDTO, String authorizationHeader);

    ContactDTO putContact(Integer id, ContactDTO contactDTO, String authorizationHeader);
}
