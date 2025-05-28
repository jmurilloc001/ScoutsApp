package com.jmurilloc.pfc.scouts.entities.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoricalTripDto
{
    
    private Long id;
    private Long tripId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Object recordBody;
    private LocalDateTime closedAt;
    
    
    public Object getRecordBody()
    {
        return recordBody;
    }
    
    
    public void setRecordBody( Object recordBody )
    {
        this.recordBody = recordBody;
    }
    
    
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
    
    
    public LocalDateTime getClosedAt()
    {
        return closedAt;
    }
    
    
    public void setClosedAt( LocalDateTime closedAt )
    {
        this.closedAt = closedAt;
    }
}
