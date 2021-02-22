package com.copernicus.contactservice.service.impl;

import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.model.Contact;
import com.copernicus.contactservice.repository.ContactRepository;
import com.copernicus.contactservice.service.interfaces.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ContactService implements IContactService {

    @Autowired
    ContactRepository contactRepository;

    /** GET **/
    public ContactDTO getContact(Integer id) {

        if (!contactRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id doesn't match any of our contacts");
        }

        ContactDTO contactDTO = new ContactDTO(contactRepository.getOne(id));

        return contactDTO;
    }

    public List<ContactDTO> getAllContact() {
        List<ContactDTO> contactDTOList = new ArrayList<>();

        for(Contact contact: contactRepository.findAll()){
            contactDTOList.add(new ContactDTO(contact));
        }

        return contactDTOList;
    }

    /** POST **/
    public ContactDTO postContact(ContactDTO contactDTO) {
        Contact contact = contactRepository.save(new Contact(contactDTO.getName(), contactDTO.getPhoneNumber(), contactDTO.getEmail(), contactDTO.getCompanyName()));
        contactDTO.setId(contact.getId());
        return contactDTO;
    }

    /** PUT **/
    public ContactDTO putContact(Integer id, ContactDTO contactDTO) {
        if (!contactRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is doesn't match any of our contacts");
        }

        Contact contact = contactRepository.save(new Contact(contactDTO.getName(), contactDTO.getPhoneNumber(), contactDTO.getEmail(), contactDTO.getCompanyName()));
        contact.setId(id);
        contactDTO.setId(contact.getId());
        return contactDTO;
    }

    /** DELETE **/
    public void deleteContact(Integer id) {
        if (!contactRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is doesn't match any of our contacts");
        }

        contactRepository.deleteById(id);
    }
}
