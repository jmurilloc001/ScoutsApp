package com.jmurilloc.pfc.scouts.util;

public enum MessageError {
    PRODUCT_NOT_FOUND,
    CREATE_ERROR,
    MEEATING_AND_AFFILIATE_NOT_FOUND,
    MEEATING_NOT_FOUND,
    AFFILIATE_NOT_FOUND;

    private String value;

    MessageError(){
        switch (this){
            case PRODUCT_NOT_FOUND -> value = "Producto no encontrado";
            case CREATE_ERROR -> value = "Error al crear el entity";
            case MEEATING_AND_AFFILIATE_NOT_FOUND -> value = "No se ha encontrado la reunión o al afiliado";
            case MEEATING_NOT_FOUND -> value = "No se ha encontrado ninguna reunion";
            case AFFILIATE_NOT_FOUND -> value = "No se ha encontrado al afiliado";
            default -> value = "Ningun error está implementado";

        }
    }
    public String getValue(){
        return value;
    }
}
