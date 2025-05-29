package com.jmurilloc.pfc.scouts.entities.dto;

import java.time.LocalDate;
import java.util.Set;

public class TripDto
{
    
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<TripMaterialDto> materials; // Cambio de Set<Product> a Set<TripMaterialDto>
    
    
    // Getters y Setters
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
    
    
    public Set<TripMaterialDto> getMaterials()
    {
        return materials;
    }
    
    
    public void setMaterials( Set<TripMaterialDto> materials )
    {
        this.materials = materials;
    }
}

