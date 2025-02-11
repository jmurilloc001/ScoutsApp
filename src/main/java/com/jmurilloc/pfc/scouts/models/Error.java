package com.jmurilloc.pfc.scouts.models;

import java.util.Date;

public class Error {

    private String message;
    private String errorSpecification;
    private int status;
    private Date date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorSpecification() {
        return errorSpecification;
    }

    public void setErrorSpecification(String errorSpecification) {
        this.errorSpecification = errorSpecification;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
