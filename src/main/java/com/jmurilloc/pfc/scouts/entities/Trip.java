package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
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
    
    @ManyToMany
    @JoinTable( name = "trip_product", joinColumns = @JoinColumn( name = "trip_id" ), inverseJoinColumns = @JoinColumn( name = "product_id" ) )
    private Set<Product> products;
    
    
    // Getters y setters
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
    
    
    public Set<Product> getProducts()
    {
        return products;
    }
    
    
    public void setMaterials( Set<Product> products )
    {
        this.products = products;
    }
    
    
    public void addProduct( Product product )
    {
        log.info( "Add product with id: {}", product.getId() );
        this.products.add( product );
    }
    
}
