package com.copernicus.contactservice.service.impl;

import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.enums.Industry;
import com.copernicus.contactservice.model.Account;
import com.copernicus.contactservice.model.Contact;
import com.copernicus.contactservice.repository.AccountRepository;
import com.copernicus.contactservice.repository.ContactRepository;
import com.copernicus.contactservice.service.interfaces.IContactService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactServiceTest {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    IContactService contactService;

    private List<Contact> contactList;
    @BeforeEach
    void setUp() {

        Account account = accountRepository.save(
                new Account(Industry.ECOMMERCE, 46, "Amarillo", "USA")
        );

   contactRepository.saveAll(List.of(
                new Contact("Paul", "123-456-789", "paul@paul.com", "Futurama", account),
                new Contact("Celia", "987-654-321", "celia@celia.com", "Futurama", account),
                new Contact("Salvatore", "000-000-000", "corsaro@corsaro.com", "The Simpsons"),
                new Contact("Nerea", "999-999-999", "nerea@nerea.com", "American Dad")
        ));

   contactList = contactRepository.findAll();
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getContact() {
        Integer id = contactList.get(3).getId();
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