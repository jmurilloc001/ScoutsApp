package com.jmurilloc.pfc.scouts.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "date_meeting",unique = true)
    private Date dateMeeting;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "educando_metting",
            joinColumns = @JoinColumn(name = "meeting_id"), inverseJoinColumns = @JoinColumn(name = "educando_id")
    )
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
//    public Map<String, Map<String,Object>> getReuniones() { //Hago este get, porque si no, estaría tdo el rato llamandose de uno a otro
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
