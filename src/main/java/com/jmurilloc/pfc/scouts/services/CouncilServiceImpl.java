package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Council;
import com.jmurilloc.pfc.scouts.repositories.CouncilRepositorory;
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
        return repositorory.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Council> findById(Long id) {
        return repositorory.findById(id);
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

        return repositorory.findByFechaInicioBetween(startDate, endDate);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repositorory.deleteById(id);
    }

    @Transactional
    @Override
    public Council save(Council council) {
        return repositorory.save(council);
    }
}
