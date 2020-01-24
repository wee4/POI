package com.traceable.poi.services;

import com.traceable.poi.model.Meeting;
import com.traceable.poi.repositories.MeetingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MeetService")
public class MeetService {

    private final MeetingRepository repository;

    public MeetService(MeetingRepository repository) {
        this.repository = repository;
    }

    public List<Meeting> findAll() {
        return repository.findAll();
    }
}
