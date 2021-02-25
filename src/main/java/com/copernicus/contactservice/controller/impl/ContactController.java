package com.copernicus.contactservice.controller.impl;

import com.copernicus.contactservice.controller.dto.AuthenticationRequest;
import com.copernicus.contactservice.controller.dto.AuthenticationResponse;
import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.controller.interfaces.IContactController;
import com.copernicus.contactservice.security.MyUserDetailsService;
import com.copernicus.contactservice.service.interfaces.IContactService;
import com.copernicus.contactservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController implements IContactController {

    @Autowired
    IContactService contactService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    private static String contactAccountAuthOk;
    private static String contactValidationAuthOk;


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
    @PutMapping("/contact/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDTO putContact(@PathVariable Integer id,@RequestBody @Valid ContactDTO contactDTO) {
        return contactService.putContact(id, contactDTO);
    }

    /** AUTHENTICATION **/
    @PostMapping("/contact/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    public static String getContactAccountAuthOk() {
        return contactAccountAuthOk;
    }

    public static void setContactAccountAuthOk(String contactAccountAuthOk) {
        ContactController.contactAccountAuthOk = contactAccountAuthOk;
    }

    public static String getContactValidationAuthOk() {
        return contactValidationAuthOk;
    }

    public static void setContactValidationAuthOk(String contactValidationAuthOk) {
        ContactController.contactValidationAuthOk = contactValidationAuthOk;
    }
}
