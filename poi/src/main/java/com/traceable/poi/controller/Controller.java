/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.controller;

import com.traceable.poi.domain.Meeting;
import com.traceable.poi.domain.Vehicle;
import com.traceable.poi.repositories.MeetingRepository;
import com.traceable.poi.repositories.VehicleRepository;
import com.traceable.poi.services.PoiService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Wesley Fermino <wesleycz@live.com>
 * Feb 17 2019
 */
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private PoiService service;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MeetingRepository meetingRepository;
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @GetMapping(value = "/meetings/{vehicle_id}")
    public List<Meeting> getMeetingsByVehicle(@PathVariable(value = "vehicle_id") Integer vehicleId) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        Vehicle v = vehicle.get();
        List<Meeting> list = meetingRepository.findAll();
        list.removeIf((Meeting t) -> !t.getPosition().getVehicle().equals(v));

        return list;
    }

    @GetMapping(value = "/meetings")
    public List<Meeting> getMeetings() {
        List<Meeting> list = meetingRepository.findAll();
        if (list.isEmpty()) {
            service.calculate();
        }
        return list;
    }

}
