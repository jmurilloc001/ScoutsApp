package com.jmurilloc.pfc.scouts.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.*;

//afiliados
@Entity
@Table(name = "affiliates")
public class Affiliate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private Date birthday;
    @Column(name = "inscripcion_date")
    private Date inscripcionDate;
    private String seccion;

    @ManyToMany(mappedBy = "educandos")
    @JsonIgnore
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

//    public Map<String,Map<String,Object>> getReuniones() { //Hago este get, porque si no, estaría tdo el rato llamandose de uno a otro
//        Map<String,Map<String,Object>> json = new HashMap<>();
//        for (Meeting reunione : reuniones) {
//            Map<String,Object> reunion = new HashMap<>();
//
//            reunion.put("Id",reunione.getId());
//            reunion.put("Date",reunione.getDateMeeting());
//            reunion.put("Número educandos",reunione.getEducandos().size());
//
//            json.put("Reunion "+ reunione.getId(),reunion);
//        }
//        return json;
//    }

    public void setReuniones(Set<Meeting> reuniones) {
        this.reuniones = reuniones;
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
