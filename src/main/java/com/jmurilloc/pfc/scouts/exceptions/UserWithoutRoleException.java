package com.jmurilloc.pfc.scouts.exceptions;

public class UserWithoutRoleException extends RuntimeException {
    public UserWithoutRoleException(String message) {
        super(message);
    }
}
