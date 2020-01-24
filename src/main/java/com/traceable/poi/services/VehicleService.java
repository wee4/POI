package com.traceable.poi.services;

import com.traceable.poi.model.Vehicle;
import com.traceable.poi.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("VehicleService")
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }


    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    public Optional<Vehicle> findById(Integer vehicleId) {
        return repository.findById(vehicleId);
    }
}
