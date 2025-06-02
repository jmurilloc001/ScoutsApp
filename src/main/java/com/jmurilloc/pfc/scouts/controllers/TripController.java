package com.jmurilloc.pfc.scouts.controllers;

import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.HistoricalTripNotCreatedException;
import com.jmurilloc.pfc.scouts.exceptions.TripNotFoundException;
import com.jmurilloc.pfc.scouts.services.interfaces.TripService;
import com.jmurilloc.pfc.scouts.util.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los viajes (Trip).
 */
@RestController
@Slf4j
@RequestMapping( "/trips" )
public class TripController
{
    
    private TripService tripService;
    
    
    /**
     * Inyecta el servicio de viajes.
     *
     * @param tripService
     *         Servicio de viajes.
     */
    @Autowired
    public void setTripService( TripService tripService )
    {
        this.tripService = tripService;
    }
    
    
    /**
     * Obtiene un viaje por su ID.
     *
     * @param id
     *         ID del viaje.
     * @return El viaje encontrado o lanza excepción si no existe.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/{id}" )
    public ResponseEntity<TripDto> getTripById( @PathVariable Long id )
    {
        log.info( "Fetching trips" );
        Optional<TripDto> optionalTripDto = tripService.getTripById( id );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip found with id: {}", id );
            return ResponseEntity.ok( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Trip not found with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Obtiene todos los viajes de forma paginada.
     *
     * @param page
     *         Número de página.
     * @param size
     *         Tamaño de la página.
     * @return Página de viajes.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/paginated" )
    public ResponseEntity<Page<TripDto>> getAllTripsPaginated( @RequestParam int page, @RequestParam int size )
    {
        log.info( "Fetching all trips with pagination: page {}, size {}", page, size );
        Page<TripDto> tripPage = tripService.getAllTripsPaginated( page, size );
        if( tripPage.hasContent() )
        {
            log.info( "Trips found: {}", tripPage.getTotalElements() );
            return ResponseEntity.ok( tripPage );
        }
        else
        {
            log.warn( "No trips found" );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Crea un nuevo viaje.
     *
     * @param tripDto
     *         Datos del viaje a crear.
     * @return El viaje creado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PostMapping
    public ResponseEntity<TripDto> createTrip( @RequestBody TripDto tripDto )
    {
        if( tripDto.getMaterials() == null || tripDto.getMaterials().isEmpty() )
        {
            log.info( "No products provided for trip, initializing empty product list." );
            tripDto.setMaterials( new HashSet<>() );
        }
        
        log.info( "Creating trip with title: {}", tripDto.getTitle() );
        
        Optional<TripDto> optionalTripDto = tripService.createTrip( tripDto );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip created successfully with id: {}", optionalTripDto.get().getId() );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to create trip" );
            throw new TripNotFoundException( MessageError.TRIP_NOT_CREATED.getValue() );
        }
    }
    
    
    /**
     * Cierra un viaje y registra las cantidades devueltas.
     *
     * @param id
     *         ID del viaje.
     * @param cantidadesDevueltas
     *         Mapa de productos y cantidades devueltas.
     * @return El viaje cerrado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PostMapping( "/{id}/close" )
    public ResponseEntity<TripDto> closeTrip( @PathVariable Long id, @RequestBody Map<String, Integer> cantidadesDevueltas )
    {
        log.info( "Cerrando trip desde el controlador." );
        
        try
        {
            Optional<TripDto> optionalTripDto = tripService.closeTrip( id, cantidadesDevueltas );
            if( optionalTripDto.isPresent() )
            {
                log.info( "Trip closed successfully with id: {}", optionalTripDto.get().getId() );
                return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
            }
            else
            {
                log.warn( "Failed to close trip with id: {}", id );
                throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
            }
        }
        catch ( BadDataException e )
        {
            log.warn( "Exception during the close trip with id: {}", id );
            throw new HistoricalTripNotCreatedException( MessageError.HISTORICAL_TRIP_NOT_CREATED.getValue() );
        }
        
    }
    
    
    /**
     * Actualiza los datos de un viaje.
     *
     * @param id
     *         ID del viaje.
     * @param tripDto
     *         Datos actualizados del viaje.
     * @return El viaje actualizado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}" )
    public ResponseEntity<TripDto> updateTrip( @PathVariable Long id, TripDto tripDto )
    {
        log.info( "Updating trip with id: {}", id );
        Optional<TripDto> optionalTripDto = tripService.updateTrip( id, tripDto );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip updated successfully with id: {}", optionalTripDto.get().getId() );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to update trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Añade un producto a un viaje.
     *
     * @param id
     *         ID del viaje.
     * @param productId
     *         ID del producto.
     * @param quantity
     *         Cantidad del producto.
     * @return El viaje actualizado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}/addProduct" )
    public ResponseEntity<TripDto> addProduct( @PathVariable Long id, @RequestParam Long productId, @RequestParam Integer quantity )
    {
        log.info( "Adding product with id: {} to trip with id: {}", productId, id );
        Optional<TripDto> optionalTripDto = tripService.addProductToTrip( id, productId, quantity );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Product added successfully to trip with id: {}", id );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to add product to trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Actualiza la cantidad de un producto en un viaje por ID de producto.
     *
     * @param id
     *         ID del viaje.
     * @param productId
     *         ID del producto.
     * @param newquantity
     *         Nueva cantidad.
     * @return El viaje actualizado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}/product/{productId}/updateQuantity/{newquantity}" )
    public ResponseEntity<TripDto> updateProductQuantity( @PathVariable Long id, @PathVariable Long productId, @PathVariable Integer newquantity )
    {
        log.info( "Actualizando la salida con id: {} y el producto con id: {}", id, productId );
        Optional<TripDto> optionalTripDto = tripService.updateTripMaterial( id, productId, newquantity );
        if( optionalTripDto.isPresent() )
        {
            log.info( "La cantidad del producto se ha actualizado satisfactoriamente para la salida con id: {}", id );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to update product quantity for trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Actualiza la cantidad de un producto en un viaje por nombre de producto.
     *
     * @param id
     *         ID del viaje.
     * @param productName
     *         Nombre del producto.
     * @param newquantity
     *         Nueva cantidad.
     * @return El viaje actualizado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @PutMapping( "/{id}/productName/{productName}/updateQuantity/{newquantity}" )
    public ResponseEntity<TripDto> updateProductByNameQuantity( @PathVariable Long id, @PathVariable String productName, @PathVariable Integer newquantity )
    {
        log.info( "Actualizando la salida con id: {} y el producto: {}", id, productName );
        Optional<TripDto> optionalTripDto = tripService.updateTripMaterialByName( id, productName, newquantity );
        if( optionalTripDto.isPresent() )
        {
            log.info( "La cantidad del producto se ha actualizado satisfactoriamente para la salida con id: {}", id );
            return ResponseEntity.status( HttpStatus.CREATED ).body( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to update product quantity for trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Elimina un viaje por su ID.
     *
     * @param id
     *         ID del viaje.
     * @return El viaje eliminado.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @DeleteMapping( "/{id}" )
    public ResponseEntity<TripDto> deleteTrip( @PathVariable Long id )
    {
        log.info( "Deleting trip with id: {}", id );
        Optional<TripDto> optionalTripDto = tripService.deleteTrip( id );
        if( optionalTripDto.isPresent() )
        {
            log.info( "Trip deleted successfully with id: {}", id );
            return ResponseEntity.ok( optionalTripDto.get() );
        }
        else
        {
            log.warn( "Failed to delete trip with id: {}", id );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
    
    
    /**
     * Obtiene todos los viajes no cerrados de forma paginada.
     *
     * @param page
     *         Número de página.
     * @param size
     *         Tamaño de la página.
     * @return Página de viajes no cerrados.
     */
    @PreAuthorize( "hasRole('SCOUTER')" )
    @GetMapping( "/paginated/close/false" )
    public ResponseEntity<Page<TripDto>> getAllTripsPaginatedByCloseFalse( @RequestParam int page, @RequestParam int size )
    {
        log.info( "Buscando salidas paginadas" );
        Page<TripDto> tripPage = tripService.getTripsPaginatedByCloseFalse( page, size );
        if( tripPage.hasContent() )
        {
            log.info( "Trips found: {}", tripPage.getTotalElements() );
            return ResponseEntity.ok( tripPage );
        }
        else
        {
            log.warn( "No trips found" );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
    }
}
