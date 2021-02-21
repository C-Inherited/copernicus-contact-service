package com.copernicus.contactservice.controller.interfaces;

import com.copernicus.contactservice.controller.dto.ContactDTO;
import java.util.List;

public interface IContactController {

    ContactDTO getContact(Integer id);

    List<ContactDTO> getAllContact();

    ContactDTO postContact(ContactDTO contactDTO);

    ContactDTO putContact(Integer id, ContactDTO contactDTO);

    void deleteContact(Integer id);

}
