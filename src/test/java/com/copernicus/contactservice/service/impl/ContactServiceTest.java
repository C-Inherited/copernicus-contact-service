package com.copernicus.contactservice.service.impl;

import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.enums.Industry;
import com.copernicus.contactservice.model.Account;
import com.copernicus.contactservice.model.Contact;
import com.copernicus.contactservice.repository.ContactRepository;
import com.copernicus.contactservice.service.interfaces.IContactService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    IContactService contactService;

    private List<Contact> contactList;
    @BeforeEach
    void setUp() {

        Account account = new Account(
                Industry.ECOMMERCE,
                46,
                "Amarillo",
                "USA"
        );

        contactList.add(contactRepository.save(
                new Contact("Paul", "123-456-789", "paul@paul.com", "Futurama")
//                new Contact("Celia", "987-654-321", "celia@celia.com", "Futurama", account),
//                new Contact("Salvatore", "000-000-000", "corsaro@corsaro.com", "The Simpsons"),
//                new Contact("Nerea", "999-999-999", "nerea@nerea.com", "American Dad")
        ));
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
    }

    @Test
    void getContact() {
        Integer id = contactList.get(0).getId();
        ContactDTO contactDTO = contactService.getContact(id);

        assertEquals("Paul", contactDTO.getName());
    }

    @Test
    void getAllContact() {
    }

    @Test
    void postContact() {
    }

    @Test
    void putContact() {
    }

    @Test
    void deleteContact() {
    }
}