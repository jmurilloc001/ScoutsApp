package com.jmurilloc.pfc.scouts.util;

public enum MessageError {
    PRODUCT_NOT_FOUND,
    CREATE_ERROR,
    MEEATING_AND_AFFILIATE_NOT_FOUND,
    MEEATING_NOT_FOUND,
    AFFILIATE_NOT_FOUND,
    AFFILIATE_IN_MEETING,
    AFFILIATE_NOT_DELETED,
    USER_NOT_FOUND,
    USER_WITH_AFFILIATE,
    AFFILIATE_WITH_USER,
    USER_NOT_DELETED,
    PRODUCT_NOT_DELETED,
    BAD_DATA,
    VALIDATE_EXISTS_PRODUCT_BY_NAME,
    VALIDATE_EXISTS_USER_BY_USERNAME,
    ROLE_NOT_FOUND;

    private String value;

    MessageError(){
        switch (this){
            case PRODUCT_NOT_FOUND -> value = "Producto no encontrado";
            case CREATE_ERROR -> value = "Error al crear el entity";
            case MEEATING_AND_AFFILIATE_NOT_FOUND -> value = "No se ha encontrado la reunión o al afiliado";
            case MEEATING_NOT_FOUND -> value = "No se ha encontrado ninguna reunion";
            case AFFILIATE_NOT_FOUND -> value = "No se ha encontrado al afiliado";
            case AFFILIATE_IN_MEETING -> value = "Ya esta ese educando en la reunion";
            case AFFILIATE_NOT_DELETED -> value = "No se ha podido borrar al educando";
            case USER_NOT_FOUND -> value = "Usuario no encontrado";
            case USER_WITH_AFFILIATE -> value = "El usuario ya tiene un afiliado";
            case AFFILIATE_WITH_USER -> value = "El afiliado ya tiene un usuario";
            case USER_NOT_DELETED -> value = "No se ha podido borrar el usuario";
            case ROLE_NOT_FOUND -> value = "No se ha encontrado el rol";
            case BAD_DATA -> value = "Datos mal introducidos";
            case VALIDATE_EXISTS_PRODUCT_BY_NAME -> value = "ya existe en la base de datos!, escoja otro nombre para el producto";
            case PRODUCT_NOT_DELETED -> value = "Error al borrar el producto";
            case VALIDATE_EXISTS_USER_BY_USERNAME -> value = "ya existe en la base de datos!, escoja otro username";
            default -> value = "Ningun error está implementado";

        }
    }
    public String getValue(){
        return value;
    }
}
