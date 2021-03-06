package com.copernicus.contactservice.controller.dto;

import com.copernicus.contactservice.model.Account;
import com.copernicus.contactservice.model.Contact;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ContactDTO {
    @Min(value = 1, message = "Minimum value for opportunity id is 1")
    private Integer id;
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Phone number is required")
    private String phoneNumber;
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Company name is required")
    private String companyName;
    @Min(value = 1, message = "Minimum value for account id is 1")
    @NotNull
    private Integer accountId;

    public ContactDTO() {
    }

    public ContactDTO(Contact contact){
        setId(contact.getId());
        setName(contact.getName());
        setPhoneNumber(contact.getPhoneNumber());
        setEmail(contact.getEmail());
        setCompanyName(contact.getCompanyName());
        setAccountId(contact.getAccount().getId());
    }

    public static ContactDTO parseFromContact(Contact contact){
        return new ContactDTO(contact);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
