package com.copernicus.contactservice.controller.dto;


import com.copernicus.contactservice.enums.ValidationType;

import javax.validation.constraints.NotNull;

public class ValidationDTO {
    private String strToValidate;
    private Integer nmbToValidate;
    @NotNull
    private ValidationType validationType;

    public ValidationDTO() {
    }

    public ValidationDTO(String strToValidate, Integer nmbToValidate, @NotNull ValidationType validationType) {
        this.strToValidate = strToValidate;
        this.nmbToValidate = nmbToValidate;
        this.validationType = validationType;
    }

    public String getStrToValidate() {
        return strToValidate;
    }

    public void setStrToValidate(String strToValidate) {
        this.strToValidate = strToValidate;
    }

    public Integer getNmbToValidate() {
        return nmbToValidate;
    }

    public void setNmbToValidate(Integer nmbToValidate) {
        this.nmbToValidate = nmbToValidate;
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
    }
}
