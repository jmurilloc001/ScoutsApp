package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Product;
import com.jmurilloc.pfc.scouts.entities.Trip;
import com.jmurilloc.pfc.scouts.entities.TripMaterial;
import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.exceptions.InsuficientStockException;
import com.jmurilloc.pfc.scouts.repositories.TripMaterialRepository;
import com.jmurilloc.pfc.scouts.repositories.TripRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.ProductService;
import com.jmurilloc.pfc.scouts.services.interfaces.TripService;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TripServiceImpl implements TripService
{
    
    private TripRepository tripRepository;
    private ProductService productService;
    private TripMaterialRepository tripMaterialRepository;
    
    
    @Autowired
    public void setTripMaterialRepository( TripMaterialRepository tripMaterialRepository )
    {
        this.tripMaterialRepository = tripMaterialRepository;
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
        try
        {
            if( tripDto.getStartDate().isAfter( tripDto.getEndDate() ) )
            {
                log.warn( "Start date must be before end date" );
                return Optional.empty();
            }
            
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
        try
        {
            log.info( "Adding product with id: {} to trip with id: {}", productId, tripId );
            
            Optional<Trip> tripOptional = tripRepository.findById( tripId );
            if( tripOptional.isEmpty() )
            {
                log.warn( "Trip with id {} not found", tripId );
                return Optional.empty();
            }
            
            Product product = productService.findById( productId );
            if( product == null )
            {
                log.warn( "Product with id {} not found", productId );
                return Optional.empty();
            }
            
            int initialStock = product.getStock();
            if( initialStock < quantity )
            {
                log.warn( "Insufficient stock for product with id {}", productId );
                throw new InsuficientStockException( "Available stock: " + product.getStock() );
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
    
}
