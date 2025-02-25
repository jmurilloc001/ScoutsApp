package com.jmurilloc.pfc.scouts.entities.dto;

import java.util.Date;

public class AffiliateDto {
    private Long id;
    private String name;
    private String lastname;
    private Date birthday;
    private Date inscripcionDate;
    private String seccion;

    public AffiliateDto(Long id,String name, String lastname, Date inscripcionDate, String seccion) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
