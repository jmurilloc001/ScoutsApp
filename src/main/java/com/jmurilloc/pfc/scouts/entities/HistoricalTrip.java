package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table( name = "historical_trips" )
public class HistoricalTrip
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @Column( name = "trip_id", nullable = false )
    private Long tripId;
    
    @Column( nullable = false )
    private String title;
    
    @Column( name = "start_date", nullable = false )
    private String startDate;
    
    @Column( name = "end_date", nullable = false )
    private String endDate;
    
    @Column( nullable = false, columnDefinition = "TEXT" )
    private String materials;
    
    @Column( name = "closed_at", nullable = false )
    private LocalDateTime closedAt = LocalDateTime.now();
    
    
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
    
    
    public String getStartDate()
    {
        return startDate;
    }
    
    
    public void setStartDate( String startDate )
    {
        this.startDate = startDate;
    }
    
    
    public String getEndDate()
    {
        return endDate;
    }
    
    
    public void setEndDate( String endDate )
    {
        this.endDate = endDate;
    }
    
    
    public String getMaterials()
    {
        return materials;
    }
    
    
    public void setMaterials( String materials )
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
