package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.Affiliate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AffiliatesRepository extends JpaRepository<Affiliate,Long> {
    Optional<Affiliate> findByName(String name);
}
