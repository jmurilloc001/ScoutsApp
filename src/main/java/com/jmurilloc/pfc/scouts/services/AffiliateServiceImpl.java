package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.projections.AffiliateMeetingProjection;
import com.jmurilloc.pfc.scouts.repositories.AffiliatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AffiliateServiceImpl implements AffiliateService {
    private AffiliatesRepository repository;

    @Autowired
    public AffiliateServiceImpl(AffiliatesRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<Affiliate> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Affiliate save(Affiliate affiliate) {
        return repository.save(affiliate);
    }

}
