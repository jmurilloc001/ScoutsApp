package com.jmurilloc.pfc.scouts.entities;

//Consejos

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Council {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "started_date")
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @PastOrPresent(message = "La fecha de inicio debe ser en el pasado o en el presente")
    private Date fechaInicio;
    @Column(name = "end_date")
    private Date fechaFin;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(
            name = "affiliates_councils",
            joinColumns = @JoinColumn(name = "council_id"), inverseJoinColumns = @JoinColumn(name = "affiliate_id")
    )
    private Set<Affiliate> asistentes;

    @Lob
    @Column(name = "pdf_new_council_points")
    @Size(max = 10485760, message = "El archivo PDF no puede exceder los 10 MB")
    private Byte[] puntosConsejo;
    @Lob
    @Column(name = "pdf_old_council_points")
    @Size(max = 10485760, message = "El archivo PDF no puede exceder los 10 MB")
    private Byte[] puntosConsejoAnterior;

    public Council() {
        asistentes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<Affiliate> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(Set<Affiliate> asistentes) {
        this.asistentes = asistentes;
    }

    public Byte[] getPuntosConsejo() {
        return puntosConsejo;
    }

    public void setPuntosConsejo(Byte[] puntosConsejo) {
        this.puntosConsejo = puntosConsejo;
    }

    public Byte[] getPuntosConsejoAnterior() {
        return puntosConsejoAnterior;
    }

    public void setPuntosConsejoAnterior(Byte[] puntosConsejoAnterior) {
        this.puntosConsejoAnterior = puntosConsejoAnterior;
    }
}
