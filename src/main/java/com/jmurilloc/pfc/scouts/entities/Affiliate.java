package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.*;

//afiliados
@Entity
@Table(name = "affiliates")
public class Affiliate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2,max = 25)
    private String name;

    @NotBlank
    @Size(min = 2,max = 25)
    private String lastname;
    private Date birthday;
    @Column(name = "inscripcion_date")
    private Date inscripcionDate;
    @NotBlank
    private String seccion;

    @ManyToMany(mappedBy = "educandos")
    private Set<Meeting> reuniones = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "affiliate")
    private User user;

    public Affiliate() {
    }

    public Affiliate(Long id, String name, String lastname, Date birthday, Date inscripcionDate, String seccion, Set<Meeting> reuniones) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.inscripcionDate = inscripcionDate;
        this.seccion = seccion;
        this.reuniones = reuniones;
    }

    public Affiliate(String name, String lastname, Date birthday, Date inscripcionDate, String seccion, Set<Meeting> reuniones) {
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.inscripcionDate = inscripcionDate;
        this.seccion = seccion;
        this.reuniones = reuniones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Meeting> getReuniones() {
        return reuniones;
    }

    public void setReuniones(Set<Meeting> reuniones) {
        this.reuniones = reuniones;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Affiliates{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", inscripcionDate=" + inscripcionDate +
                ", seccion=" + seccion +
                '}';
    }
}
