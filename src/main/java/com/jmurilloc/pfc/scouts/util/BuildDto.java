package com.jmurilloc.pfc.scouts.util;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import com.jmurilloc.pfc.scouts.entities.New;
import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.entities.Role;
import com.jmurilloc.pfc.scouts.entities.Trip;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.entities.dto.AffiliateDto;
import com.jmurilloc.pfc.scouts.entities.dto.CouncilDto;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import com.jmurilloc.pfc.scouts.entities.dto.NewDto;
import com.jmurilloc.pfc.scouts.entities.dto.PostDto;
import com.jmurilloc.pfc.scouts.entities.dto.RoleDto;
import com.jmurilloc.pfc.scouts.entities.dto.TripDto;
import com.jmurilloc.pfc.scouts.entities.dto.TripMaterialDto;
import com.jmurilloc.pfc.scouts.entities.dto.UserDto;
import com.jmurilloc.pfc.scouts.exceptions.CouncilDtoException;
import com.jmurilloc.pfc.scouts.exceptions.NewDtoException;
import com.jmurilloc.pfc.scouts.exceptions.TripDtoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BuildDto
{
    
    private BuildDto()
    {
    
    }
    
    
    public static AffiliateDto buildDto( Affiliate affiliate )
    {
        AffiliateDto affiliateDto = new AffiliateDto( affiliate.getId(), affiliate.getName(), affiliate.getLastname(), affiliate.getInscripcionDate(), affiliate.getSeccion() );
        affiliateDto.setBirthday( (affiliate.getBirthday() != null)?affiliate.getBirthday():null );
        return affiliateDto;
    }
    
    
    public static UserDto builDto( User user )
    {
        UserDto dto = new UserDto( user.getUsername() );
        
        dto.setId( user.getId() );
        
        if( user.getAffiliate() != null )
        {
            dto.setName( user.getAffiliate().getName() );
            dto.setLastname( user.getAffiliate().getLastname() );
            dto.setAffiliateId( user.getAffiliate().getId() );
        }
        List<RoleDto> roleDtos = new ArrayList<>();
        user.getRoles().forEach( role -> roleDtos.add( BuildDto.buildDto( role ) ) );
        dto.setRoles( roleDtos );
        dto.setEnabled( user.isEnabled() );
        
        return dto;
    }
    
    
    public static RoleDto buildDto( Role role )
    {
        RoleDto roleDto = new RoleDto();
        if( role.getName() != null )
        {
            roleDto.setName( role.getName() );
        }
        return roleDto;
    }
    
    
    public static CouncilDto buildDto( Council council )
    {
        CouncilDto councilDto = new CouncilDto();
        try
        {
            councilDto.setInitialDate( council.getFechaInicio() );
            councilDto.setEndDate( council.getFechaFin() );
            councilDto.setId( council.getId() );
        }
        catch ( CouncilDtoException e )
        {
            throw new CouncilDtoException( "No se ha podido crear el ConcilDto" );
        }
        return councilDto;
    }
    
    
    public static PostDto buildPostDto( Post post )
    {
        PostDto postDto = new PostDto();
        try
        {
            postDto.setId( post.getId() );
            postDto.setDescription( post.getDescription() );
            postDto.setType( post.getType().name() );
            postDto.setAffiliateDto( buildDto( post.getAffiliate() ) );
            postDto.setTitle( post.getTitle() );
            if( post.getEmail() != null )
            {
                postDto.setEmail( post.getEmail() );
            }
            if( post.getTlf() != null )
            {
                postDto.setTlf( post.getTlf() );
            }
        }
        catch ( Exception e )
        {
            throw new CouncilDtoException( "No se ha podido crear el PostDto" );
        }
        return postDto;
    }
    
    
    public static NewDto buildDto( New newEntity )
    {
        NewDto newDto = new NewDto();
        try
        {
            newDto.setId( newEntity.getId() );
            newDto.setTitle( newEntity.getTitle() );
            newDto.setUrlImage( newEntity.getUrlImage() );
            newDto.setDescription( newEntity.getDescription() );
            newDto.setDate( newEntity.getDate() );
            newDto.setUpdatedAt( newEntity.getUpdatedAt() );
            newDto.setCreatedAt( newEntity.getCreatedAt() );
            
            return newDto;
        }
        catch ( Exception e )
        {
            throw new NewDtoException( MessageError.NEWDTO_COULD_NOT_BE_CREATED.getValue() );
        }
    }
    
    
    public static List<NewDto> buildListDto( List<New> newList )
    {
        try
        {
            List<NewDto> newDtoList = new ArrayList<>();
            for( New newEntity : newList )
            {
                newDtoList.add( BuildDto.buildDto( newEntity ) );
            }
            return newDtoList;
        }
        catch ( Exception e )
        {
            throw new NewDtoException( MessageError.NEWDTO_COULD_NOT_BE_CREATED.getValue() );
        }
        
    }
    
    
    public static TripDto buildDto( Trip trip )
    {
        try
        {
            TripDto tripDto = new TripDto();
            tripDto.setId( trip.getId() );
            tripDto.setTitle( trip.getTitle() );
            tripDto.setStartDate( trip.getStartDate() );
            tripDto.setEndDate( trip.getEndDate() );
            
            // Convertir los materiales del viaje a TripMaterialDto
            Set<TripMaterialDto> materialsDto = trip.getTripMaterials().stream().map( tripMaterial -> {
                TripMaterialDto dto = new TripMaterialDto();
                dto.setProductId( tripMaterial.getProduct().getId() );
                dto.setProductName( tripMaterial.getProduct().getName() );
                dto.setCantidad( tripMaterial.getCantidad() );
                return dto;
            } ).collect( Collectors.toSet() );
            
            tripDto.setMaterials( materialsDto );
            
            return tripDto;
        }
        catch ( Exception e )
        {
            throw new TripDtoException( MessageError.NEWDTO_COULD_NOT_BE_CREATED.getValue() );
        }
    }
    
    
    public static HistoricalTripDto buildDto( HistoricalTrip historicalTrip )
    {
        try
        {
            HistoricalTripDto historicalTripDto = new HistoricalTripDto();
            historicalTripDto.setTripId( historicalTrip.getTripId() );
            historicalTripDto.setId( historicalTrip.getId() );
            historicalTripDto.setTitle( historicalTrip.getTitle() );
            historicalTripDto.setStartDate( historicalTrip.getStartDate() );
            historicalTripDto.setEndDate( historicalTrip.getEndDate() );
            historicalTripDto.setClosedAt( historicalTrip.getClosedAt() );
            
            historicalTripDto.setRecordBody( historicalTrip.getRecordBody() );
            
            return historicalTripDto;
        }
        catch ( Exception e )
        {
            throw new TripDtoException( MessageError.HISTORICAL_TRIPDTO_NOT_CREATED.getValue() );
        }
    }
}
