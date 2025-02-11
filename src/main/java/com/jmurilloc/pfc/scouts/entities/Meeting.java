package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//Reuniones
@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_meeting")
    private Date dateMeeting;

    @ManyToMany(mappedBy = "reuniones")
    private Set<Affiliate> educandos = new HashSet<>();

    public Meeting() {
    }

    public Meeting(Long id, Date date, Set<Affiliate> educandos) {
        this.id = id;
        this.dateMeeting = date;
        this.educandos = educandos;
    }

    public Meeting(Date date, Set<Affiliate> educandos) {
        this.dateMeeting = date;
        this.educandos = educandos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateMeeting() {
        return dateMeeting;
    }

    public void setDateMeeting(Date dateMeeting) {
        this.dateMeeting = dateMeeting;
    }

    public Set<Affiliate> getEducandos() {
        return educandos;
    }

    public void setEducandos(Set<Affiliate> educandos) {
        this.educandos = educandos;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", date=" + dateMeeting +
                ", educandos=" + educandos +
                '}';
    }
}
