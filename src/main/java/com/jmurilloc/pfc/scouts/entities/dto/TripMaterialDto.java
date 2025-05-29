package com.jmurilloc.pfc.scouts.entities.dto;

public class TripMaterialDto
{
    
    private Long productId;
    private String productName;
    private Integer cantidad;
    
    
    // Getters y Setters
    public Long getProductId()
    {
        return productId;
    }
    
    
    public void setProductId( Long productId )
    {
        this.productId = productId;
    }
    
    
    public String getProductName()
    {
        return productName;
    }
    
    
    public void setProductName( String productName )
    {
        this.productName = productName;
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

