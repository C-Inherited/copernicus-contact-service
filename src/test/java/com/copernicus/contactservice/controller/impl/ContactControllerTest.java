package com.copernicus.contactservice.controller.impl;

import com.copernicus.contactservice.client.AccountClient;
import com.copernicus.contactservice.controller.dto.AccountDTO;
import com.copernicus.contactservice.controller.dto.ContactDTO;
import com.copernicus.contactservice.enums.Industry;
import com.copernicus.contactservice.model.Account;
import com.copernicus.contactservice.model.Contact;
import com.copernicus.contactservice.repository.AccountRepository;
import com.copernicus.contactservice.repository.ContactRepository;
import com.copernicus.contactservice.service.impl.ContactService;
import com.copernicus.contactservice.service.interfaces.IContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ContactControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    AccountClient accountClient;

    @Autowired
    AccountRepository accountRepository;

    @MockBean
    private ContactService contactService;
    private AccountDTO accountDto;
    private ContactDTO contactDTO;
    private Account account;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        accountDto = new AccountDTO(1, Industry.ECOMMERCE.name(), 5130, "Amsterdam", "Netherlands");
        account = new Account(accountDto);
        contactDTO =
                new ContactDTO(new Contact(1, "Paul Pototo", "666666666", "paul@paul.com", "Futurama", account));

    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getContact_Ok() throws Exception {
        String expectedResponse = "{\"id\":1,\"name\":\"Paul Pototo\",\"phoneNumber\":\"666666666\",\"email\":\"paul@paul.com\",\"companyName\":\"Futurama\",\"accountId\":1}";
        when(contactService.getContact(1)).thenReturn(contactDTO);

        MvcResult result = mockMvc
                .perform(
                        get("/contact/1")
                                .header("Authorization", "Bearer auth"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    void getAllContact_Ok() throws Exception {
        String expectedResponse = "[{\"id\":1,\"name\":\"Paul Pototo\",\"phoneNumber\":\"666666666\",\"email\":\"paul@paul.com\",\"companyName\":\"Futurama\",\"accountId\":1}]";
        when(contactService.getAllContact()).thenReturn(List.of(contactDTO));

        MvcResult result = mockMvc
                .perform(
                        get("/contact/")
                                .header("Authorization", "Bearer auth"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expectedResponse, result.getResponse().getContentAsString());
    }

    @Test
    void postContact_Ok() throws Exception {
        String expectedResponse = "{\"id\":1,\"name\":\"Paul Pototo\",\"phoneNumber\":\"666666666\",\"email\":\"paul@paul.com\",\"companyName\":\"Futurama\",\"accountId\":1}";
        when(contactService.postContact(contactDTO)).thenReturn(contactDTO);

        String body = objectMapper.writeValueAsString(contactDTO);

        MvcResult result = mockMvc
                .perform(
                        post("/new/contact/")
                                .header("Authorization", "Bearer auth")
                                .content(body).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void putContact_Ok() throws Exception {
        String expectedResponse = "{\"id\":1,\"name\":\"Paul Pototo\",\"phoneNumber\":\"666666666\",\"email\":\"paul@paul.com\",\"companyName\":\"Futurama\",\"accountId\":1}";
        when(contactService.putContact(1, contactDTO)).thenReturn(contactDTO);

        String body = objectMapper.writeValueAsString(contactDTO);

        MvcResult result = mockMvc
                .perform(
                        get("/contact/1")
                                .header("Authorization", "Bearer auth")
                                .content(body).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }
}