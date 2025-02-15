package com.jmurilloc.pfc.scouts.util;

public enum RoleNames {
    USER,
    ADMIN;

    private String value;

    RoleNames(){
        switch (this){
            case USER -> value = "ROLE_USER";
            case ADMIN -> value = "ROLE_ADMIN";
            default -> value = "Ningun ROL está implementado";

        }
    }
    public String getValue(){
        return value;
    }
}
