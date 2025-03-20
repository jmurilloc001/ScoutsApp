package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.exceptions.CouncilNotFoundException;
import com.jmurilloc.pfc.scouts.repositories.CouncilRepositorory;
import com.jmurilloc.pfc.scouts.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CouncilServiceImpl implements CouncilService{

    private CouncilRepositorory repositorory;

    @Autowired
    public void setRepositorory(CouncilRepositorory repositorory) {
        this.repositorory = repositorory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Council> listAll() {
        List<Council> allCouncils = repositorory.findAll();
        if (allCouncils.isEmpty()) throw new CouncilNotFoundException(MessageError.COUNCIL_NOT_FOUND.getValue());
        return allCouncils;
    }

    @Transactional(readOnly = true)
    @Override
    public Council findById(Long id) {
        Optional<Council> byId = repositorory.findById(id);
        if (byId.isEmpty()) throw new CouncilNotFoundException(MessageError.COUNCIL_NOT_FOUND.getValue());
        return byId.orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Council> findByInitialDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDate = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date endDate = cal.getTime();

        List<Council> byFechaInicioBetween = repositorory.findByFechaInicioBetween(startDate, endDate);
        if (byFechaInicioBetween.isEmpty()) throw new CouncilNotFoundException(MessageError.COUNCIL_NOT_FOUND.getValue());
        return byFechaInicioBetween;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Council> council = repositorory.findById(id);
        if (council.isEmpty()) {
            throw new CouncilNotFoundException(MessageError.COUNCIL_NOT_FOUND.getValue());
        }
        repositorory.deleteById(id);
    }

    @Transactional
    @Override
    public Council save(Council council) {
        return repositorory.save(council);
    }
}
