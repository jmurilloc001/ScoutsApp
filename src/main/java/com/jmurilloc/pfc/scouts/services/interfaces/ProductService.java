package com.jmurilloc.pfc.scouts.services.interfaces;

import com.jmurilloc.pfc.scouts.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductService
{
    
    List<Product> productsWithPriceGreaterThan( float amount );
    
    
    List<Product> productsWithPriceLessThan( float amount );
    
    
    List<Product> productsWithLastPurcharseAfter( Date date );
    
    
    List<Product> productsWithLastPurcharseBefore( Date date );
    
    
    List<Product> listAllProducts();
    
    
    Page<Product> listAllProducts( Pageable pageable );
    
    
    Product saveProduct( Product product );
    
    
    void deleteProduct( Long id );
    
    
    Product findById( Long id );
    
    
    boolean existsByName( String name );
    
    
    Optional<Product> findByName( String name );
    
    
    Integer stockbyName( String name );
    
}
