package com.copernicus.contactservice.service.impl;

import com.copernicus.contactservice.client.AccountClient;
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
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactServiceTest {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AccountClient accountClient;

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
                new Contact("Paul Patoto", "666666666", "paul@paul.com", "Futurama", account),
                new Contact("Celia Patata", "987654321", "celia@celia.com", "Futurama", account),
                new Contact("Salvatore Potete", "676767676", "corsaro@corsaro.com", "The Simpsons", account2),
                new Contact("Nerea Patita", "999999999", "nerea@nerea.com", "American Dad", account2)
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
        assertNotEquals(987654321, contactDTO.getPhoneNumber());
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
        Account account = accountRepository.save(new Account(
                Industry.ECOMMERCE, 22, "Amsterdam", "Netherlands"
        ));
        ContactDTO contactDTO = new ContactDTO(new Contact("Ruben Kenobi", "666666666", "maythe4thbewithyou@newrepublic.com", "Kashyyyk S.L.", account));
        contactService.postContact(contactDTO);
        Integer id = contactDTO.getId();
        assertEquals(contactRepository.findById(id).get().getCompanyName(), "Kashyyyk S.L.");
    }

    @Test
    void putContact() {
        ContactDTO contactDTO = new ContactDTO(contactList.get(0));
        contactDTO.setCompanyName("Kashyyyk S.L.");
        Integer id = contactList.get(0).getId();
        contactService.putContact(id, contactDTO);
        assertEquals(contactRepository.findById(id).get().getCompanyName(), "Kashyyyk S.L.");
    }

    @Test
    void checkAccountCreateContact_PostRequestNotAccount_Error(){
        Account account = new Account(Industry.ECOMMERCE, 22, "Amsterdam", "Netherlands");
        ContactDTO contactDTO = new ContactDTO(new Contact("Paul Kenobi", "123456789", "paul@paul.com", "Kashyyyk S.L.", account));
        assertThrows(ResponseStatusException.class, () ->contactService.postContact(contactDTO));
    }

    @Test
    void checkAccountCreateContact_PutRequestNotAccount_Error(){
        Account account = new Account(Industry.ECOMMERCE, 22, "Amsterdam", "Netherlands");
        ContactDTO contactDTO = new ContactDTO(new Contact("Paul Kenobi", "123456789", "paul@paul.com", "Kashyyyk S.L.", account));
        assertThrows(InvalidDataAccessApiUsageException.class, () ->contactService.putContact(contactDTO.getId(), contactDTO));
    }

    @Test
    void validationContact_PostRequestBadValidationName_Error(){
        Account account = new Account(Industry.ECOMMERCE, 22, "Amsterdam", "Netherlands");
        ContactDTO contactDTO = new ContactDTO(new Contact("1J!a", "123456789", "paul@paul.com", "Kashyyyk S.L.", account));
        assertThrows(ResponseStatusException.class, () ->contactService.postContact(contactDTO));
    }

    @Test
    void validationContact_PostRequestBadValidationEmail_Error(){
        Account account = new Account(Industry.ECOMMERCE, 22, "Amsterdam", "Netherlands");
        ContactDTO contactDTO = new ContactDTO(new Contact("Paul Kenobi", "123456789", "paul@paul", "Kashyyyk S.L.", account));
        assertThrows(ResponseStatusException.class, () ->contactService.postContact(contactDTO));
    }

    @Test
    void validationContact_PostRequestBadValidationPhone_Error(){
        Account account = new Account(Industry.ECOMMERCE, 22, "Amsterdam", "Netherlands");
        ContactDTO contactDTO = new ContactDTO(new Contact("Paul Kenobi", "12", "paul@paul.com", "Kashyyyk S.L.", account));
        assertThrows(ResponseStatusException.class, () ->contactService.postContact(contactDTO));
    }

}