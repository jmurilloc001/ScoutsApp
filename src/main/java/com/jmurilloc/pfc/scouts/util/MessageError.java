package com.jmurilloc.pfc.scouts.util;

public enum MessageError {
    PRODUCT_NOT_FOUND,
    CREATE_ERROR;

    private String value;

    MessageError(){
        switch (this){
            case PRODUCT_NOT_FOUND -> value = "Producto no encontrado";
            case CREATE_ERROR -> value = "Error al crear el entity";
            default -> value = "Ningun error está implementado";

        }
    }
    public String getValue(){
        return value;
    }
}
