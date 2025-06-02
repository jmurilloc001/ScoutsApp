package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import com.jmurilloc.pfc.scouts.repositories.HistoricalTripRepository;
import com.jmurilloc.pfc.scouts.services.interfaces.HistoricalTripService;
import com.jmurilloc.pfc.scouts.util.BuildDto;
import com.jmurilloc.pfc.scouts.util.BuildEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con los HistoricalTrip.
 */
@Slf4j
@Service
public class HistoricalTripServiceImpl implements HistoricalTripService
{
    
    @Autowired
    private HistoricalTripRepository historicalTripRepository;
    
    
    /**
     * Obtiene un HistoricalTrip por su ID.
     *
     * @param id
     *         ID del HistoricalTrip.
     * @return Optional con el DTO del HistoricalTrip si existe.
     */
    @Transactional( readOnly = true )
    @Override
    public Optional<HistoricalTripDto> getHistoricalTripById( Long id )
    {
        try
        {
            log.info( "Buscando un historical trip con id: {}", id );
            Optional<HistoricalTrip> historicalTrip = historicalTripRepository.findById( id );
            if( historicalTrip.isEmpty() )
            {
                log.warn( "Historical trip con id {} no encontrado", id );
                return Optional.empty();
            }
            log.info( "Historical trip encontrado: {}", historicalTrip.get() );
            HistoricalTrip trip = historicalTrip.get();
            return Optional.of( BuildDto.buildDto( trip ) );
        }
        catch ( Exception e )
        {
            return Optional.empty();
        }
    }
    
    
    /**
     * Obtiene un HistoricalTrip por el ID del Trip original.
     *
     * @param tripId
     *         ID del Trip original.
     * @return Optional con el DTO del HistoricalTrip si existe.
     */
    @Transactional( readOnly = true )
    @Override
    public Optional<HistoricalTripDto> getHistoricalTripByTripId( Long tripId )
    {
        try
        {
            log.info( "Buscando un historical trip con tripId: {}", tripId );
            Optional<HistoricalTrip> historicalTrip = historicalTripRepository.findByTripId( tripId );
            if( historicalTrip.isEmpty() )
            {
                log.warn( "Historical trip con tripId {} no encontrado", tripId );
                return Optional.empty();
            }
            log.info( "Historical trip encontrado: {}", historicalTrip.get() );
            HistoricalTrip trip = historicalTrip.get();
            return Optional.of( BuildDto.buildDto( trip ) );
        }
        catch ( Exception e )
        {
            return Optional.empty();
        }
    }
    
    
    /**
     * Obtiene todos los HistoricalTrip de forma paginada.
     *
     * @param page
     *         Número de página.
     * @param size
     *         Tamaño de la página.
     * @return Página de DTOs de HistoricalTrip.
     */
    @Transactional( readOnly = true )
    @Override
    public Page<HistoricalTripDto> getAllHistoricalTripsPaginated( int page, int size )
    {
        try
        {
            log.info( "Obteniendo todos los historical trips paginados, página: {}, tamaño: {}", page, size );
            Pageable pageable = Pageable.ofSize( size ).withPage( page );
            Page<HistoricalTrip> historicalTrips = historicalTripRepository.findAll( pageable );
            if( historicalTrips.isEmpty() )
            {
                log.warn( "No se encontraron historical trips" );
                return Page.empty();
            }
            log.info( "Historical trips encontrados: {}", historicalTrips.getContent() );
            return historicalTrips.map( BuildDto::buildDto );
        }
        catch ( Exception e )
        {
            return Page.empty();
        }
        
    }
    
    
    /**
     * Crea un nuevo HistoricalTrip.
     *
     * @param historicalTripDto
     *         DTO con los datos del HistoricalTrip a crear.
     * @return Optional con el DTO del HistoricalTrip creado.
     */
    @Transactional
    @Override
    public Optional<HistoricalTripDto> createHistoricalTrip( HistoricalTripDto historicalTripDto )
    {
        try
        {
            log.info( "Creando un nuevo historical trip: {}", historicalTripDto );
            HistoricalTrip historicalTrip = BuildEntity.buildHistoricalTrip( historicalTripDto );
            HistoricalTrip savedHistoricalTrip = historicalTripRepository.save( historicalTrip );
            log.info( "Historical trip creado: {}", savedHistoricalTrip );
            return Optional.of( BuildDto.buildDto( savedHistoricalTrip ) );
        }
        catch ( Exception e )
        {
            log.error( "Error al crear el historical trip: {}", e.getMessage() );
            return Optional.empty();
        }
    }
    
    
    /**
     * Actualiza un HistoricalTrip existente.
     *
     * @param id
     *         ID del HistoricalTrip a actualizar.
     * @param historicalTrip
     *         Entidad con los nuevos datos.
     * @return Optional con el DTO del HistoricalTrip actualizado.
     */
    @Transactional
    @Override
    public Optional<HistoricalTripDto> updateHistoricalTrip( Long id, HistoricalTrip historicalTrip )
    {
        try
        {
            log.info( "Actualizando historical trip con id: {}", id );
            Optional<HistoricalTrip> existingTrip = historicalTripRepository.findById( id );
            if( existingTrip.isEmpty() )
            {
                log.warn( "Historical trip con id {} no encontrado para actualizar", id );
                return Optional.empty();
            }
            HistoricalTrip updatedTrip = existingTrip.get();
            updatedTrip.setTitle( historicalTrip.getTitle() );
            updatedTrip.setStartDate( historicalTrip.getStartDate() );
            updatedTrip.setEndDate( historicalTrip.getEndDate() );
            updatedTrip.setRecordBody( historicalTrip.getRecordBody() );
            updatedTrip.setClosedAt( historicalTrip.getClosedAt() );
            
            HistoricalTrip savedHistoricalTrip = historicalTripRepository.save( updatedTrip );
            log.info( "Historical trip actualizado: {}", savedHistoricalTrip );
            return Optional.of( BuildDto.buildDto( savedHistoricalTrip ) );
        }
        catch ( Exception e )
        {
            log.error( "Error al actualizar el historical trip: {}", e.getMessage() );
            return Optional.empty();
        }
        
    }
    
    
    /**
     * Elimina un HistoricalTrip por su ID.
     *
     * @param id
     *         ID del HistoricalTrip a eliminar.
     * @return Optional con el DTO del HistoricalTrip eliminado.
     */
    @Transactional
    @Override
    public Optional<HistoricalTripDto> deleteHistoricalTrip( Long id )
    {
        try
        {
            log.info( "Eliminando historical trip con id: {}", id );
            Optional<HistoricalTrip> historicalTrip = historicalTripRepository.findById( id );
            if( historicalTrip.isEmpty() )
            {
                log.warn( "Historical trip con id {} no encontrado para eliminar", id );
                return Optional.empty();
            }
            historicalTripRepository.deleteById( id );
            log.info( "Historical trip eliminado con id: {}", id );
            return Optional.of( BuildDto.buildDto( historicalTrip.get() ) );
        }
        catch ( Exception e )
        {
            log.error( "Error al eliminar el historical trip con id {}: {}", id, e.getMessage() );
            return Optional.empty();
        }
    }
}
