package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import com.jmurilloc.pfc.scouts.repositories.AffiliatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AffiliateServiceImpl implements AffiliateService {
    private AffiliatesRepository repository;

    @Autowired
    public AffiliateServiceImpl(AffiliatesRepository repository) {
        this.repository = repository;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Affiliate> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Affiliate save(Affiliate affiliate) {
        return repository.save(affiliate);
    }

}
