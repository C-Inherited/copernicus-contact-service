package com.copernicus.contactservice.service.impl;

import com.copernicus.contactservice.client.AccountClient;
import com.copernicus.contactservice.client.ValidationClient;
import com.copernicus.contactservice.controller.dto.AccountDTO;
import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.controller.dto.ValidationDTO;
import com.copernicus.contactservice.enums.ValidationType;
import com.copernicus.contactservice.model.Account;
import com.copernicus.contactservice.model.Contact;
import com.copernicus.contactservice.repository.ContactRepository;
import com.copernicus.contactservice.service.interfaces.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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

    @Autowired
    AccountClient accountClient;

    @Autowired
    ValidationClient validationClient;

    private final CircuitBreakerFactory circuitBreakerFactory = new Resilience4JCircuitBreakerFactory();

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
        validationContact(contactDTO);
        contactDTO.setId(checkAccountCreateContact(contactDTO).getId());
        return contactDTO;
    }

    /** PUT **/
    public ContactDTO putContact(Integer id, ContactDTO contactDTO) {
        if (!contactRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is doesn't match any of our contacts");
        }
        validationContact(contactDTO);
        checkAccountCreateContact(contactDTO).setId(id);
        contactDTO.setId(checkAccountCreateContact(contactDTO).getId());
        return contactDTO;
    }

    /** DELETE **/
    public void deleteContact(Integer id) {
        if (!contactRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This is doesn't match any of our contacts");
        }
        contactRepository.deleteById(id);
    }

    /** VALIDATION METHODS **/

    private Contact checkAccountCreateContact(ContactDTO contactDTO) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("contact-service");
        AccountDTO accountDTO = circuitBreaker.run(() -> accountClient.getAccount(contactDTO.getAccountId()), throwable -> contactCache());
        Account account = new Account(accountDTO);
        return contactRepository.save(new Contact(contactDTO.getName(), contactDTO.getPhoneNumber(), contactDTO.getEmail(), contactDTO.getCompanyName(), account));
    }

    private void validationContact(ContactDTO contactDTO){
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("contact-service");
        circuitBreaker.run(() -> validationClient.checkIsNameValid(new ValidationDTO(contactDTO.getName(), null, ValidationType.NAME)), throwable -> notValidName());
        circuitBreaker.run(() -> validationClient.checkIsEmailValid(new ValidationDTO(contactDTO.getEmail(), null, ValidationType.NAME)), throwable -> notValidEmail());
        circuitBreaker.run(() -> validationClient.checkIsPhoneNumberValid(new ValidationDTO(null, contactDTO.getPhoneNumber(), ValidationType.NAME)), throwable -> notValidPhone());
    }

    /** THROWABLE **/

    private AccountDTO contactCache() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account id doesn't match any of our accounts.");
    }

    private Boolean notValidEmail() {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your email format is not correct.");
    }

    private Boolean notValidPhone() {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your phone number format is not correct.");
    }

    private Boolean notValidName() {
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Your name format is not correct.");
    }
}
