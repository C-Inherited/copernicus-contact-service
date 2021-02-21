package com.copernicus.contactservice.controller.impl;

import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.controller.interfaces.IContactController;
import com.copernicus.contactservice.service.interfaces.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController implements IContactController {

    @Autowired
    IContactService contactService;

    /** GET **/
    @GetMapping("/contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO getContact(@PathVariable Integer id) {
        return contactService.getContact(id);
    }

    /** GET **/
    @GetMapping("/contact/")
    @ResponseStatus(HttpStatus.OK)
    public List<ContactDTO> getAllContact() {
        return contactService.getAllContact();
    }

    /** POST **/
    @PostMapping("/new/contact/")
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO postContact(@RequestBody @Valid ContactDTO contactDTO) {
        return contactService.postContact(contactDTO);
    }

    /** PUT **/
    @PutMapping("contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO putContact(@PathVariable Integer id,@RequestBody @Valid ContactDTO contactDTO) {
        return contactService.putContact(id, contactDTO);
    }

    /** DELETE **/
    @DeleteMapping("contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteContact(Integer id) {
        contactService.deleteContact(id);
    }
}
