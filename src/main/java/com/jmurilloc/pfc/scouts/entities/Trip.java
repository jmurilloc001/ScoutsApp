package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "trips" )
public class Trip
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    private String title;
    
    @Column( name = "start_date" )
    private LocalDate startDate;
    
    @Column( name = "end_date" )
    private LocalDate endDate;
    
    @OneToMany( mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<TripMaterial> tripMaterials = new HashSet<>();
    
    @Column( name = "close" )
    private boolean close;
    
    
    public Long getId()
    {
        return id;
    }
    
    
    public void setId( Long id )
    {
        this.id = id;
    }
    
    
    public String getTitle()
    {
        return title;
    }
    
    
    public void setTitle( String title )
    {
        this.title = title;
    }
    
    
    public LocalDate getStartDate()
    {
        return startDate;
    }
    
    
    public void setStartDate( LocalDate startDate )
    {
        this.startDate = startDate;
    }
    
    
    public LocalDate getEndDate()
    {
        return endDate;
    }
    
    
    public void setEndDate( LocalDate endDate )
    {
        this.endDate = endDate;
    }
    
    
    public Set<TripMaterial> getTripMaterials()
    {
        return tripMaterials;
    }
    
    
    public void setTripMaterials( Set<TripMaterial> tripMaterials )
    {
        this.tripMaterials = tripMaterials;
    }
    
    
    public void addMaterial( Product product, Integer cantidad )
    {
        TripMaterial tripMaterial = new TripMaterial();
        tripMaterial.setTrip( this );
        tripMaterial.setProduct( product );
        tripMaterial.setCantidad( cantidad );
        this.tripMaterials.add( tripMaterial );
    }
    
    
    public boolean isClose()
    {
        return close;
    }
    
    
    public void setClose( boolean close )
    {
        this.close = close;
    }
}

