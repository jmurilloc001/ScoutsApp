package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
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
    private LocalDate startDate;
    
    @Column( name = "end_date", nullable = false )
    private LocalDate endDate;
    
    @Column( name = "record_body", columnDefinition = "jsonb" )
    private Object recordBody;
    
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
    
    
    public Object getRecordBody()
    {
        return recordBody;
    }
    
    
    public void setRecordBody( Object recordBody )
    {
        this.recordBody = recordBody;
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
