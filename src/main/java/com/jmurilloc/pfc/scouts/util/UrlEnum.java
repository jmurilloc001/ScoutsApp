package com.jmurilloc.pfc.scouts.util;

public enum UrlEnum {
    GET_AFFILIATE_BY_ID;

    private String value;
    private String raiz;

    UrlEnum(){
        raiz = "http://localhost:8080/";
        switch (this){
            case GET_AFFILIATE_BY_ID -> value = raiz+"affiliates/";
            default -> value = "NADA";
        }
    }
    public String getValue(){
        return value;
    }
}
