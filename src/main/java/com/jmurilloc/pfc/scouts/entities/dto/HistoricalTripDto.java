package com.jmurilloc.pfc.scouts.entities.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class HistoricalTripDto
{
    
    private Long id;
    private Long tripId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, Object> materials;
    private LocalDateTime closedAt;
    
    
    public Long getId()
    {
        return id;
    }
    
    
    public void setId( Long id )
    {
        this.id = id;
    }
    
    
    public Long getTripId()
    {
        return tripId;
    }
    
    
    public void setTripId( Long tripId )
    {
        this.tripId = tripId;
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
    
    
    public Map<String, Object> getMaterials()
    {
        return materials;
    }
    
    
    public void setMaterials( Map<String, Object> materials )
    {
        this.materials = materials;
    }
    
    
    public LocalDateTime getClosedAt()
    {
        return closedAt;
    }
    
    
    public void setClosedAt( LocalDateTime closedAt )
    {
        this.closedAt = closedAt;
    }
}
