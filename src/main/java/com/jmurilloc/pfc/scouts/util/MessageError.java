package com.jmurilloc.pfc.scouts.util;

public enum MessageError {
    PRODUCT_NOT_FOUND;

    private String value;

    MessageError(){
        switch (this){
            case PRODUCT_NOT_FOUND -> value = "Producto no encontrado";
            default -> value = "Ningun error está implementado";

        }
    }
    public String getValue(){
        return value;
    }
}
