package com.jmurilloc.pfc.scouts.entities.dto;

import java.util.Date;

public class NewDto
{
    
    private Long id;
    private String title;
    private String urlImage;
    private String description;
    private Date date;
    private Date createdAt;
    private Date updatedAt;
    
    
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
    
    
    public String getUrlImage()
    {
        return urlImage;
    }
    
    
    public void setUrlImage( String urlImage )
    {
        this.urlImage = urlImage;
    }
    
    
    public String getDescription()
    {
        return description;
    }
    
    
    public void setDescription( String description )
    {
        this.description = description;
    }
    
    
    public Date getDate()
    {
        return date;
    }
    
    
    public void setDate( Date date )
    {
        this.date = date;
    }
    
    
    public Date getCreatedAt()
    {
        return createdAt;
    }
    
    
    public void setCreatedAt( Date createdAt )
    {
        this.createdAt = createdAt;
    }
    
    
    public Date getUpdatedAt()
    {
        return updatedAt;
    }
    
    
    public void setUpdatedAt( Date updatedAt )
    {
        this.updatedAt = updatedAt;
    }
}
