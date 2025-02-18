package com.jmurilloc.pfc.scouts.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RoleNames {
    USER,
    COORDI,
    SCOUTER,
    ADMIN;

    private static List<String> namesRoles = Arrays.asList("USER","COORDI","SCOUTER","ADMIN");

    private String value;

    RoleNames(){
        switch (this){
            case USER -> value = "ROLE_USER";
            case COORDI -> value = "ROLE_COORDI";
            case SCOUTER -> value = "ROLE_SCOUTER";
            case ADMIN -> value = "ROLE_ADMIN";
            default -> value = "Ningún ROL está implementado";

        }
    }
    public String getValue(){ return value; }
    public static List<String> getNamesRoles(){ return namesRoles; }
    public static RoleNames getRoleWithString(String s){
        switch (s){
            case "USER" -> {
                return RoleNames.USER;
            }
            case "COORDI" -> {
                return RoleNames.COORDI;
            }
            case "ADMIN" -> {
                return RoleNames.ADMIN;
            }
            case "SCOUTER" -> {
                return RoleNames.SCOUTER;
            }
            default -> {
                return null;
            }
        }
    }
}
