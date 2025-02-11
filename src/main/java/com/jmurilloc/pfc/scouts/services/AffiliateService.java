package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Affiliate;

import java.util.Optional;

public interface AffiliateService {

    Optional<Affiliate> findById(Long id);
    Affiliate save(Affiliate affiliate);
}
