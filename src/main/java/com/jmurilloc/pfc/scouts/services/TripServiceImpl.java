package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.entities.Trip;
import com.jmurilloc.pfc.scouts.entities.TripMaterial;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.BadDataException;
import com.jmurilloc.pfc.scouts.exceptions.HistoricalTripNotCreatedException;
import com.jmurilloc.pfc.scouts.exceptions.InsuficientStockException;
import com.jmurilloc.pfc.scouts.exceptions.ProductNotFoundException;
import com.jmurilloc.pfc.scouts.exceptions.TripNotFoundException;
import com.jmurilloc.pfc.scouts.repositories.TripMaterialRepository;
import com.jmurilloc.pfc.scouts.repositories.TripRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.HistoricalTripService;
import com.jmurilloc.pfc.scouts.services.interfaces.ProductService;
import com.jmurilloc.pfc.scouts.services.interfaces.TripService;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import com.jmurilloc.pfc.scouts.util.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class TripServiceImpl implements TripService
{
    
    private TripRepository tripRepository;
    private HistoricalTripService historicalTripService;
    private ProductService productService;
    private TripMaterialRepository tripMaterialRepository;
    
    
    @Autowired
    public void setTripMaterialRepository( TripMaterialRepository tripMaterialRepository )
    {
        this.tripMaterialRepository = tripMaterialRepository;
    }
    
    
    @Autowired
    public void setHistoricalTripService( HistoricalTripService historicalTripService )
    {
        this.historicalTripService = historicalTripService;
    }
    
    
    @Autowired
    public void setProductService( ProductService productService )
    {
        this.productService = productService;
    }
    
    
    @Autowired
    public void setTripRepository( TripRepository tripRepository )
    {
        this.tripRepository = tripRepository;
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Optional<TripDto> getTripById( Long id )
    {
        log.info( "Searching trip with id: {}", id );
        Optional<Trip> trip = tripRepository.findById( id );
        if( trip.isPresent() )
        {
            log.info( "Trip found: {}", trip.get() );
            return Optional.of( BuildDto.buildDto( trip.get() ) );
        }
        return Optional.empty();
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public Optional<TripDto> getTripByTitle( String title )
    {
        try
        {
            log.info( "Searching trip with title: {}", title );
            Optional<Trip> trip = tripRepository.findByTitle( title );
            if( trip.isPresent() )
            {
                log.info( "Trip found: {}", trip.get() );
                return Optional.of( BuildDto.buildDto( trip.get() ) );
            }
            return Optional.empty();
        }
        catch ( Exception e )
        {
            log.error( "Error searching trip by title: {}", e.getMessage() );
            return Optional.empty();
        }
        
    }
    
    
    @Transactional( readOnly = true )
    @Override
    public List<TripDto> getAllTrips()
    {
        try
        {
            log.info( "Retrieving all trips" );
            List<Trip> trips = tripRepository.findAll();
            if( !trips.isEmpty() )
            {
                log.info( "Trips found: {}", trips );
                return trips.stream().map( BuildDto::buildDto ).toList();
            }
            return List.of();
        }
        catch ( Exception e )
        {
            log.error( "Error retrieving all trips: {}", e.getMessage() );
            return List.of();
        }
    }
    
    
    @Override
    public Page<TripDto> getAllTripsPaginated( int page, int size )
    {
        try
        {
            log.info( "Retrieving all trips with pagination: page {}, size {}", page, size );
            Pageable pageable = PageRequest.of( page, size );
            Page<Trip> tripsPage = tripRepository.findAll( pageable );
            
            return tripsPage.map( BuildDto::buildDto );
        }
        catch ( Exception e )
        {
            log.error( "Error retrieving paginated trips: {}", e.getMessage() );
            return Page.empty();
        }
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> createTrip( TripDto tripDto )
    {
        if( tripDto.getStartDate().isAfter( tripDto.getEndDate() ) )
        {
            log.warn( "Start date must be before end date" );
            throw new BadDataException( "La fecha de inicio tiene que ser antes que la de fin" );
        }
        try
        {
            log.info( "Creating trip with title: {}", tripDto.getTitle() );
            Trip trip = new Trip();
            trip.setTitle( tripDto.getTitle() );
            trip.setStartDate( tripDto.getStartDate() );
            trip.setEndDate( tripDto.getEndDate() );
            
            Trip savedTrip = tripRepository.save( trip );
            log.info( "Trip created with id: {}", savedTrip.getId() );
            return Optional.of( BuildDto.buildDto( savedTrip ) );
        }
        catch ( Exception e )
        {
            log.error( "Error creating trip: {}", e.getMessage() );
            return Optional.empty();
        }
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> updateTrip( Long id, TripDto tripDto )
    {
        log.info( "Updating trip with id: {}", id );
        Optional<Trip> tripOptional = tripRepository.findById( id );
        if( tripOptional.isPresent() )
        {
            Trip trip = tripOptional.get();
            trip.setTitle( tripDto.getTitle() );
            trip.setStartDate( tripDto.getStartDate() );
            trip.setEndDate( tripDto.getEndDate() );
            
            Trip updatedTrip = tripRepository.save( trip );
            log.info( "Trip updated with id: {}", updatedTrip.getId() );
            return Optional.of( BuildDto.buildDto( updatedTrip ) );
        }
        return Optional.empty();
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> deleteTrip( Long id )
    {
        try
        {
            log.info( "Deleting trip with id: {}", id );
            Optional<Trip> tripOptional = tripRepository.findById( id );
            if( tripOptional.isPresent() )
            {
                Trip trip = tripOptional.get();
                Set<TripMaterial> tripMaterials = trip.getTripMaterials();
                if( !tripMaterials.isEmpty() )
                {
                    log.warn( "Devolviendo las cantidades de los materiales de la salida {}", id );
                    for( TripMaterial tripMaterial : tripMaterials )
                    {
                        Product product = tripMaterial.getProduct();
                        product.setStock( product.getStock() + tripMaterial.getCantidad() );
                        productService.saveProduct( product );
                        log.info( "Devolviendo stock del producto con id: {} a la cantidad: {}", product.getId(), product.getStock() );
                    }
                }
                tripRepository.deleteById( id );
                log.info( "Trip deleted with id: {}", id );
                return Optional.of( BuildDto.buildDto( tripOptional.get() ) );
            }
            return Optional.empty();
        }
        catch ( Exception e )
        {
            log.error( "Error deleting trip with id {}: {}", id, e.getMessage() );
            return Optional.empty();
        }
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> addProductToTrip( Long tripId, Long productId, Integer quantity )
    {
        Optional<TripMaterial> existingTripMaterial = tripMaterialRepository.findByTripIdAndProductId( tripId, productId );
        if( existingTripMaterial.isPresent() )
        {
            log.warn( "El producto con id {} ya está añadido a la salida con id {}", productId, tripId );
            throw new BadDataException( "El producto ya está añadido a la salida" );
        }
        try
        {
            log.info( "Añadiendo producto con id: {} a la salida con id: {}", productId, tripId );
            
            Optional<Trip> tripOptional = tripRepository.findById( tripId );
            if( tripOptional.isEmpty() )
            {
                log.warn( "Salida con id {} no encontrada", tripId );
                return Optional.empty();
            }
            
            Product product = productService.findById( productId );
            if( product == null )
            {
                log.warn( "Producto con id {} no encontrado", productId );
                return Optional.empty();
            }
            
            int initialStock = product.getStock();
            if( initialStock < quantity )
            {
                log.warn( "Stock insuficiente para el producto con id {}", productId );
                throw new InsuficientStockException( "Stock disponivle: " + product.getStock() );
            }
            
            Trip trip = tripOptional.get();
            
            product.setStock( initialStock - quantity );
            productService.saveProduct( product );
            
            TripMaterial tripMaterial = new TripMaterial();
            tripMaterial.setTrip( trip );
            tripMaterial.setProduct( product );
            tripMaterial.setCantidad( quantity );
            
            trip.getTripMaterials().add( tripMaterial );
            tripMaterialRepository.save( tripMaterial );
            
            Trip updatedTrip = tripRepository.save( trip );
            
            log.info( "Product with id: {} added to trip with id: {}, quantity: {}", productId, tripId, quantity );
            return Optional.of( BuildDto.buildDto( updatedTrip ) );
        }
        catch ( Exception e )
        {
            log.error( "Error adding product to trip: {}", e.getMessage() );
            return Optional.empty();
        }
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> updateTripMaterial( Long tripId, Long productId, Integer newQuantity )
    {
        log.info( "Actualizando la salida con id: {}, Nueva cantidad del producto con id: {}", tripId, productId );
        Optional<TripMaterial> tripMaterialOptional = tripMaterialRepository.findByTripIdAndProductId( tripId, productId );
        if( tripMaterialOptional.isEmpty() )
        {
            log.warn( "No se ha encontrado ningún TripMaterial con la id del trip {} y el id del producto {}", tripId, productId );
            throw new BadDataException( "No se ha encontrado el tripMaterial" );
        }
        
        TripMaterial tripMaterial = tripMaterialOptional.get();
        int currentQuantity = tripMaterial.getCantidad();
        
        if( newQuantity < 0 )
        {
            log.warn( "La cantidad nueva no puede ser negativa" );
            throw new BadDataException( "La cantidad nueva no puede ser negativa" );
        }
        
        Product product = tripMaterial.getProduct();
        product.setStock( product.getStock() + currentQuantity );
        
        log.info( "Devolviendo el antiguo stock del producto con nombre {}", product.getName() );
        Product productUpdate = productService.saveProduct( product );
        if( productUpdate.getStock() < newQuantity )
        {
            log.warn( "Stock insuficiente para el producto con id {}", productId );
            throw new InsuficientStockException( "Stock disponible: " + product.getStock() );
        }
        productUpdate.setStock( productUpdate.getStock() - newQuantity );
        log.info( "Actualizando el stock del producto al nuevo stock" );
        productService.saveProduct( productUpdate );
        log.info( "Actualizando tripMaterial de la cantidad inicial {} a la nueva cantidad {}", currentQuantity, newQuantity );
        tripMaterial.setCantidad( newQuantity );
        tripMaterialRepository.save( tripMaterial );
        return Optional.of( BuildDto.buildDto( tripMaterial.getTrip() ) );
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> updateTripMaterialByName( Long tripId, String productName, Integer newQuantity )
    {
        log.info( "Actualizando la salida con id: {}, Nueva cantidad del producto con nombre: {}", tripId, productName );
        Optional<Product> byName = productService.findByName( productName );
        if( byName.isEmpty() )
        {
            log.warn( "No se ha encontrado ningún producto con el nombre {}", productName );
            throw new ProductNotFoundException( MessageError.PRODUCT_NOT_FOUND.getValue() );
        }
        return updateTripMaterial( tripId, byName.get().getId(), newQuantity );
    }
    
    
    @Transactional
    @Override
    public Optional<TripDto> closeTrip( Long tripId, Map<String, Integer> cantidadDevuelta ) throws BadDataException
    {
        log.info( "Closing trip with id: {}", tripId );
        Optional<Trip> tripOptional = tripRepository.findById( tripId );
        if( tripOptional.isEmpty() )
        {
            log.warn( "No se ha encontrado ningún Trip con la id {}", tripId );
            throw new TripNotFoundException( MessageError.TRIP_NOT_FOUND.getValue() );
        }
        Trip trip = tripOptional.get();
        log.info( "Creando el HistoricalTrip para el trip con id: {}", tripId );
        HistoricalTripDto historicalTripDto = new HistoricalTripDto();
        historicalTripDto.setClosedAt( LocalDateTime.now() );
        historicalTripDto.setTripId( tripId );
        historicalTripDto.setStartDate( trip.getStartDate() );
        historicalTripDto.setEndDate( trip.getEndDate() );
        historicalTripDto.setTitle( trip.getTitle() );
        
        Set<TripMaterial> tripMaterials = trip.getTripMaterials();
        Map<String, Object> recordBodyToSave = new HashMap<>();
        
        log.info( "Guardando los materiales con su cantidad en el record body de HistoricalTrip" );
        for( TripMaterial tripMaterial : tripMaterials )
        {
            String name = tripMaterial.getProduct().getName();
            Integer cantidad = tripMaterial.getCantidad();
            
            recordBodyToSave.put( name, cantidad );
            
            if( cantidadDevuelta.containsKey( name ) )
            {
                Integer cantidadDevueltaProducto = cantidadDevuelta.get( name );
                if( cantidadDevueltaProducto > cantidad )
                {
                    log.warn( "ERROR: La cantidad que se recibe, es mayor que la que se gastó en un comienzo." );
                    throw new BadDataException( "La cantidad devuelta no puede ser mayor a la cantidad utilizada" );
                }
                log.info( "Guardando tripMaterial con la cantidad nueva devuelta " );
                tripMaterial.setCantidad( cantidadDevueltaProducto );
                tripMaterial.getProduct().setStock( tripMaterial.getProduct().getStock() + cantidadDevueltaProducto );
                tripMaterialRepository.save( tripMaterial );
            }
        }
        historicalTripDto.setRecordBody( recordBodyToSave );
        log.info( "Guardando el historicalTrip" );
        Optional<HistoricalTripDto> historicalTrip = historicalTripService.createHistoricalTrip( historicalTripDto );
        if( historicalTrip.isEmpty() )
        {
            log.error( "Error al crear el HistoricalTrip" );
            throw new HistoricalTripNotCreatedException( MessageError.HISTORICAL_TRIP_NOT_CREATED.getValue() );
        }
        log.info( "Poniendo close a true" );
        trip.setClose( true );
        tripRepository.save( trip );
        log.info( "FINALIZADO: Devolviendo el trip con id: {}", tripId );
        return Optional.of( BuildDto.buildDto( trip ) );
    }
    
}
