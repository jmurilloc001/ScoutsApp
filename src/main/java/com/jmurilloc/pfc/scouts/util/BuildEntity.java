package com.jmurilloc.pfc.scouts.util;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.entities.HistoricalTrip;
import com.jmurilloc.pfc.scouts.entities.Post;
import com.jmurilloc.pfc.scouts.entities.dto.AffiliateDto;
import com.jmurilloc.pfc.scouts.entities.dto.HistoricalTripDto;
import com.jmurilloc.pfc.scouts.entities.dto.PostDto;
import com.jmurilloc.pfc.scouts.exceptions.AffiliateCouldntCreateException;
import com.jmurilloc.pfc.scouts.exceptions.HistoricalTripNotCreatedException;
import com.jmurilloc.pfc.scouts.exceptions.PostCouldntCreateException;

public abstract class BuildEntity
{
    
    private BuildEntity()
    {
    }
    
    
    public static Affiliate buildAffiliate( AffiliateDto affiliateDto )
    {
        Affiliate affiliate = new Affiliate();
        try
        {
            affiliate.setId( affiliateDto.getId() );
            affiliate.setName( affiliateDto.getName() );
            affiliate.setBirthday( affiliateDto.getBirthday() );
            affiliate.setSeccion( affiliateDto.getSeccion() );
            affiliate.setLastname( affiliateDto.getLastname() );
            affiliate.setInscripcionDate( affiliateDto.getInscripcionDate() );
        }
        catch ( RuntimeException e )
        {
            throw new AffiliateCouldntCreateException( MessageError.AFFILIATE_NOT_CREATED.getValue() );
        }
        return affiliate;
    }
    
    
    public static Post buildPost( PostDto postDto )
    {
        Post post = new Post();
        try
        {
            post.setId( postDto.getId() );
            post.setDescription( postDto.getDescription() );
            post.setType( PostType.valueOf( postDto.getType() ) );
            post.setAffiliate( buildAffiliate( postDto.getAffiliateDto() ) );
            post.setTitle( postDto.getTitle() );
            if( postDto.getEmail() != null )
            {
                post.setEmail( postDto.getEmail() );
            }
            if( postDto.getTlf() != null )
            {
                post.setTlf( postDto.getTlf() );
            }
        }
        catch ( RuntimeException e )
        {
            throw new PostCouldntCreateException( MessageError.POST_NOT_CREATED.getValue() );
        }
        return post;
    }
    
    
    public static HistoricalTrip buildHistoricalTrip( HistoricalTripDto historicalTripDto )
    {
        HistoricalTrip historicalTrip = new HistoricalTrip();
        try
        {
            historicalTrip.setId( historicalTripDto.getId() );
            historicalTrip.setTripId( historicalTripDto.getTripId() );
            historicalTrip.setClosedAt( historicalTripDto.getClosedAt() );
            historicalTrip.setTitle( historicalTripDto.getTitle() );
            historicalTrip.setEndDate( historicalTripDto.getEndDate() );
            historicalTrip.setStartDate( historicalTripDto.getStartDate() );
            historicalTrip.setRecordBody( historicalTripDto.getRecordBody() );
        }
        catch ( Exception e )
        {
            throw new HistoricalTripNotCreatedException( MessageError.HISTORICAL_TRIP_NOT_CREATED.getValue() );
        }
        return historicalTrip;
    }
}
