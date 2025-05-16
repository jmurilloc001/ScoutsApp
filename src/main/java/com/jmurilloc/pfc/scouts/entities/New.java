package com.jmurilloc.pfc.scouts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity( name = "news" )
@EntityListeners( AuditingEntityListener.class )
public class New
{
    
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    
    @NotBlank
    private String title;
    
    @Column( name = "url_image" )
    private String urlImage;
    
    @NotBlank
    private String description;
    
    private Date date;
    
    @CreatedDate
    @CreationTimestamp
    @Column( updatable = false )
    private Date createdAt;
    
    @LastModifiedDate
    @UpdateTimestamp
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
