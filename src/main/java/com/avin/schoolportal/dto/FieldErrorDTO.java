package com.avin.schoolportal.dto;

import java.io.Serializable;

/**
 * Created by Yubar on 10/30/2016.
 */
public class FieldErrorDTO implements Serializable {
    private String error;
    private String field;

    public FieldErrorDTO() {
    }

    public FieldErrorDTO(String error, String field) {
        this.error = error;
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
