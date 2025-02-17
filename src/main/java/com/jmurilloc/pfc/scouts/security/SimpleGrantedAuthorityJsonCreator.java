package com.jmurilloc.pfc.scouts.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role){ //Este es el contructor original de la clase SimpleGrantedAuthority y lo que quiero es que cambiarlo, para que acepte otro parámetro
    }
}
