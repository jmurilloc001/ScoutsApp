package com.jmurilloc.pfc.scouts.util;

public enum MessageError
{
    PRODUCT_NOT_FOUND, PRODUCT_NOT_SAVE, CREATE_ERROR, MEEATING_AND_AFFILIATE_NOT_FOUND, MEEATING_NOT_FOUND, AFFILIATE_NOT_FOUND, AFFILIATE_IN_MEETING, AFFILIATE_NOT_DELETED, USER_NOT_FOUND, USER_WITH_AFFILIATE, AFFILIATE_WITH_USER, AFFILIATE_NOT_CREATED, USER_NOT_DELETED, PRODUCT_NOT_DELETED, BAD_DATA, VALIDATE_EXISTS_PRODUCT_BY_NAME, VALIDATE_EXISTS_USER_BY_USERNAME, USER_NOT_HAVE_ROLE, BAD_FORMAT_JSON, ACCES_DENIED_USER, COUNCIL_NOT_FOUND, COUNCIL_NOT_CREATED, POST_NEED_AFFILIATE, POST_NEED_DESCRIPTION, POST_NEED_TYPE, POST_NOT_FOUND, POST_NEED_ID, POST_NEED_TITLE, POST_NOT_CREATED, ROLE_NOT_FOUND, NEWDTO_COULD_NOT_BE_CREATED, NEW_NOT_FOUND, NEW_NOT_CREATE, TRIPDTO_NOT_CREATED, TRIP_NOT_FOUND, TRIP_NOT_CREATED, HISTORICAL_TRIP_NOT_FOUND, HISTORICAL_TRIPDTO_NOT_CREATED, HISTORICAL_TRIP_NOT_CREATED;
    
    private final String value;
    
    
    MessageError()
    {
        switch ( this )
        {
            case PRODUCT_NOT_FOUND -> value = "Producto no encontrado";
            case PRODUCT_NOT_SAVE -> value = "Error al guardar el producto";
            case CREATE_ERROR -> value = "Error al crear el entity";
            case MEEATING_AND_AFFILIATE_NOT_FOUND -> value = "No se ha encontrado la reunión o al afiliado";
            case MEEATING_NOT_FOUND -> value = "No se ha encontrado ninguna reunion";
            case AFFILIATE_NOT_FOUND -> value = "No se ha encontrado al afiliado";
            case AFFILIATE_IN_MEETING -> value = "Ya esta ese educando en la reunion";
            case AFFILIATE_NOT_DELETED -> value = "No se ha podido borrar al educando";
            case USER_NOT_FOUND -> value = "Usuario no encontrado";
            case USER_WITH_AFFILIATE -> value = "El usuario ya tiene un afiliado";
            case AFFILIATE_WITH_USER -> value = "El afiliado ya tiene un usuario";
            case AFFILIATE_NOT_CREATED -> value = "No se ha podido crear el afiliado";
            case USER_NOT_DELETED -> value = "No se ha podido borrar el usuario";
            case ROLE_NOT_FOUND -> value = "No se ha encontrado el rol";
            case BAD_DATA -> value = "Datos mal introducidos";
            case VALIDATE_EXISTS_PRODUCT_BY_NAME -> value = "ya existe en la base de datos!, escoja otro nombre para el producto";
            case PRODUCT_NOT_DELETED -> value = "Error al borrar el producto";
            case VALIDATE_EXISTS_USER_BY_USERNAME -> value = "ya existe en la base de datos!, escoja otro username";
            case BAD_FORMAT_JSON -> value = "Los campos del JSON, están mal escritos";
            case USER_NOT_HAVE_ROLE -> value = "El usuario no tiene ese rol";
            case ACCES_DENIED_USER -> value = "No eres el usuario";
            case COUNCIL_NOT_FOUND -> value = "No se ha encontrado el consejo";
            case COUNCIL_NOT_CREATED -> value = "No se ha podido crear el consejo";
            case POST_NEED_AFFILIATE -> value = "El post necesita tener un afiliado";
            case POST_NEED_DESCRIPTION -> value = "El post necesita tener una descripción";
            case POST_NEED_TYPE -> value = "El post necesita tener un tipo";
            case POST_NOT_FOUND -> value = "No se ha encontrado el post";
            case POST_NEED_ID -> value = "El post necesita tener un id";
            case POST_NOT_CREATED -> value = "No se ha podido crear el post";
            case POST_NEED_TITLE -> value = "El post necesita tener un título";
            case NEWDTO_COULD_NOT_BE_CREATED -> value = "No se ha podido crear el DTO";
            case NEW_NOT_FOUND -> value = "No se ha encontrado la noticia";
            case NEW_NOT_CREATE -> value = "No se ha podido crear la noticia";
            case TRIPDTO_NOT_CREATED -> value = "No se ha podido crear el DTO de la salida";
            case TRIP_NOT_FOUND -> value = "No se ha encontrado la salida";
            case TRIP_NOT_CREATED -> value = "No se ha podido crear la salida";
            case HISTORICAL_TRIP_NOT_FOUND -> value = "No se ha encontrado la salida histórica";
            case HISTORICAL_TRIPDTO_NOT_CREATED -> value = "No se ha podido crear el DTO de la salida histórica";
            case HISTORICAL_TRIP_NOT_CREATED -> value = "No se ha podido crear la salida histórica";
            default -> value = "Ningún error está implementado";
            
        }
    }
    
    
    public String getValue()
    {
        return value;
    }
}
