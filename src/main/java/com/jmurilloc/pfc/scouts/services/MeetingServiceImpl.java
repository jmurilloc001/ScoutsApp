package com.jmurilloc.pfc.scouts.services;

import com.jmurilloc.pfc.scouts.entities.Meeting;
import com.jmurilloc.pfc.scouts.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService{

    private MeetingRepository repository;

    @Autowired
    public MeetingServiceImpl(MeetingRepository repository){
        this.repository = repository;
    }

    @Override
    public Optional<Meeting> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Meeting save(Meeting meeting) {
        return repository.save(meeting);
    }

    @Override
    public List<Meeting> findAll() {
        return repository.findAll();
    }
}
