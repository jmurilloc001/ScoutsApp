package com.jmurilloc.pfc.scouts.repositories;

import com.jmurilloc.pfc.scouts.entities.Council;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CouncilRepositorory extends JpaRepository<Council, Long> {
    List<Council> findByFechaInicioBetween(Date startDate, Date endDate);
}
