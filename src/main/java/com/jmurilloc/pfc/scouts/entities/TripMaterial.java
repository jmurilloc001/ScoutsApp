package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "trip_material" )
public class TripMaterial
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @ManyToOne
    @JoinColumn( name = "trip_id", nullable = false )
    private Trip trip;
    
    @ManyToOne
    @JoinColumn( name = "product_id", nullable = false )
    private Product product;
    
    @Column( nullable = false )
    private Integer cantidad;
    
    
    public Long getId()
    {
        return id;
    }
    
    
    public void setId( Long id )
    {
        this.id = id;
    }
    
    
    public Trip getTrip()
    {
        return trip;
    }
    
    
    public void setTrip( Trip trip )
    {
        this.trip = trip;
    }
    
    
    public Product getProduct()
    {
        return product;
    }
    
    
    public void setProduct( Product product )
    {
        this.product = product;
    }
    
    
    public Integer getCantidad()
    {
        return cantidad;
    }
    
    
    public void setCantidad( Integer cantidad )
    {
        this.cantidad = cantidad;
    }
}

