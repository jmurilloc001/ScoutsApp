package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.services.interfaces.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith( MockitoExtension.class )
class ProductControllerTest
{
    
    @InjectMocks
    private ProductController productController;
    
    @Mock
    private ProductService productService;
    
    
    @Test
    void updateTests()
    {
        Long productId = 1L;
        
        Product existingProduct = new Product();
        existingProduct.setId( productId );
        existingProduct.setName( "Old Product" );
        existingProduct.setPrice( 100.0f );
        existingProduct.setStock( 10 );
        
        Product updatedProduct = new Product();
        updatedProduct.setId( productId );
        updatedProduct.setName( "Updated Product" );
        updatedProduct.setPrice( 150.0f );
        updatedProduct.setStock( 20 );
        
        Mockito.when( productService.findById( productId ) ).thenReturn( existingProduct );
        Mockito.when( productService.saveProduct( Mockito.any( Product.class ) ) ).thenReturn( updatedProduct );
        
        ResponseEntity<Object> response = productController.update( updatedProduct, Mockito.mock( BindingResult.class ), productId );
        
        assertEquals( HttpStatus.CREATED, response.getStatusCode() );
        assertEquals( updatedProduct, response.getBody() );
        verify( productService ).findById( productId );
        verify( productService ).saveProduct( updatedProduct );
    }
    
    
    @Test
    void updateSomeFieldsTest()
    {
        Long productId = 1L;
        
        Product existingProduct = new Product();
        existingProduct.setId( productId );
        existingProduct.setName( "Old Product" );
        existingProduct.setPrice( 100.0f );
        existingProduct.setStock( 10 );
        
        Product partialUpdate = new Product();
        partialUpdate.setName( "Updated Product" );
        partialUpdate.setPrice( 150.0f );
        partialUpdate.setStock( 20 );
        
        Product updatedProduct = new Product();
        updatedProduct.setId( productId );
        updatedProduct.setName( "Updated Product" );
        updatedProduct.setPrice( 150.0f );
        updatedProduct.setStock( 20 );
        
        Mockito.when( productService.findById( productId ) ).thenReturn( existingProduct );
        Mockito.when( productService.saveProduct( Mockito.any( Product.class ) ) ).thenReturn( updatedProduct );
        
        ResponseEntity<Object> response = productController.updateSomeFields( partialUpdate, Mockito.mock( BindingResult.class ), productId );
        
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( updatedProduct, response.getBody() );
        verify( productService ).findById( productId );
        verify( productService ).saveProduct( existingProduct );
    }
    
    
    @Test
    void deleteTest()
    {
        Long productId = 1L;
        
        Product product = new Product();
        product.setId( productId );
        
        // Mockear la búsqueda del producto antes de eliminar
        Mockito.when( productService.findById( productId ) ).thenReturn( product );
        
        // Llamar al métdo delete
        ResponseEntity<String> response = productController.delete( productId );
        
        // Verificar que el estado de la respuesta sea OK
        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( "Producto eliminado con éxito", response.getBody() );
        
        // Verificar que el métdo delete del servicio fue llamado
        verify( productService ).deleteProduct( product.getId() );
    }
    
}