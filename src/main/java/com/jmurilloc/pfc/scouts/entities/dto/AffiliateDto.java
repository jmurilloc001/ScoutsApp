package com.jmurilloc.pfc.scouts.entities.dto;

import java.util.Date;

public class AffiliateDto {
    private String name;
    private String lastname;
    private Date birthday;
    private Date inscripcionDate;
    private String seccion;
    private String username;

    public AffiliateDto(String name, String lastname, Date inscripcionDate, String seccion) {
        this.name = name;
        this.lastname = lastname;
        this.inscripcionDate = inscripcionDate;
        this.seccion = seccion;
    }

    public AffiliateDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getInscripcionDate() {
        return inscripcionDate;
    }

    public void setInscripcionDate(Date inscripcionDate) {
        this.inscripcionDate = inscripcionDate;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
