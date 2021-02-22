package com.copernicus.contactservice.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountDTO {


    private Integer id;
    @NotEmpty
    private String industry;
    @NotNull
    @Min(value = 0, message = "can not less than 0")
    private Integer employeeCount;
    @NotEmpty
    private String city;
    @NotEmpty
    private String country;


    public AccountDTO() {
    }

    public AccountDTO(Integer id, @NotEmpty String industry, @NotNull @Min(value = 0, message = "can not less than 0") Integer employeeCount, @NotEmpty String city, @NotEmpty String country) {
        this.id = id;
        this.industry = industry;
        this.employeeCount = employeeCount;
        this.city = city;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
