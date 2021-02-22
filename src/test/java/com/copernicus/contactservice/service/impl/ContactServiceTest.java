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
import org.springframework.web.server.ResponseStatusException;

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

        Account account = accountRepository.save(new Account(
                Industry.ECOMMERCE, 389, "Amarillo", "USA"
        ));

        Account account2 = accountRepository.save(new Account(
                Industry.MANUFACTURING, 10009, "Detroit", "USA"
        ));

        contactRepository.saveAll(List.of(
                new Contact("Paul", "123-456-789", "paul@paul.com", "Futurama", account),
                new Contact("Celia", "987-654-321", "celia@celia.com", "Futurama", account),
                new Contact("Salvatore", "000-000-000", "corsaro@corsaro.com", "The Simpsons", account2),
                new Contact("Nerea", "999-999-999", "nerea@nerea.com", "American Dad", account2)
        ));

        contactList = contactRepository.findAll();
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getContact_CorrectId_GetCorrectContact() {
        Integer id = contactList.get(0).getId();
        ContactDTO contactDTO = contactService.getContact(id);

        assertEquals("Paul", contactDTO.getName());
        assertNotEquals("987-654-321", contactDTO.getPhoneNumber());
    }

    @Test
    void getContact_IncorrectId_ThrowError() {
       assertThrows(ResponseStatusException.class, () ->contactService.getContact(1));
    }

    @Test
    void getAllContacts_CheckSize_CorrectSize() {
        assertEquals(4, contactService.getAllContact().size());
    }

    @Test
    void postContact_NewContact_SavedToDatabase() {
        ContactDTO contactDTO = new ContactDTO(new Contact("Ruben Kenobi", "444-444-444", "maythe4thbewithyou@newrepublic.com", "Kashyyyk S.L.", accountRepository.findAll().get(0)));
        contactService.postContact(contactDTO);
        Integer id = contactDTO.getId();
        assertEquals(contactRepository.findById(id).get().getCompanyName(), "Kashyyyk S.L.");
    }

    @Test
    void postContact_NewBadContact_ThrowError() {
        ContactDTO contactDTO = new ContactDTO(new Contact("Ruben Kenobi", "666", "maythe4thbewithyou@newrepublic.com", "Kashyyyk S.L.", accountRepository.findAll().get(99)));
        contactService.postContact(contactDTO);
        Integer id = contactDTO.getId();
        assertEquals(contactRepository.findById(id).get().getCompanyName(), "Kashyyyk S.L.");
    }

    @Test
    void putContact() {
    }

    @Test
    void deleteContact() {
    }
}