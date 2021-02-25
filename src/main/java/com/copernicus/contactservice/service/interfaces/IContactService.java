package com.copernicus.contactservice.service.interfaces;

import com.copernicus.contactservice.controller.dto.ContactDTO;

import java.util.List;

public interface IContactService {

    ContactDTO getContact(Integer id);
    List<ContactDTO> getAllContact();
    ContactDTO postContact(ContactDTO contactDTO);
    ContactDTO putContact(Integer id, ContactDTO contactDTO);

}
