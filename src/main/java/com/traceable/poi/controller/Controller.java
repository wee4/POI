/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.controller;

import com.traceable.poi.model.Meeting;
import com.traceable.poi.model.Vehicle;
import com.traceable.poi.services.MeetService;
import com.traceable.poi.services.PoiService;
import com.traceable.poi.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Wesley Fermino <wesleycz@live.com>
 * Feb 17 2019
 */
@RestController
@RequestMapping("/api")
public class Controller {

    private final PoiService poiService;

    private final VehicleService vehicleService;

    private final MeetService meetService;

    @Autowired
    public Controller(PoiService poiService, VehicleService vehicleService, MeetService meetService) {
        this.poiService = poiService;
        this.vehicleService = vehicleService;
        this.meetService = meetService;
    }

    @GetMapping(value = "/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.findAll();
    }

    @GetMapping(value = "/meetings/{vehicle_id}/{startdate}/{enddate}")
    public List<Meeting> getMeetingsByVehicle(
            @PathVariable(value = "vehicle_id") Integer vehicleId,
            @PathVariable(value = "startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable(value = "enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Optional<Vehicle> vehicle = vehicleService.findById(vehicleId);
        Vehicle v = vehicle.orElseThrow(null);
        List<Meeting> list = meetService.findAll();

        /**
         * Usado removeif temporariamente até implementar as consultas especializadas através do repository
         */
        list.removeIf((Meeting t) -> !t.getPosition().getVehicle().equals(v));

        if (startDate != null) {
            list.removeIf((Meeting t) -> !(t.getPosition().getSentDate().after(startDate)));
        }

        if (endDate != null) {
            list.removeIf((Meeting t) -> !(t.getPosition().getSentDate().before(endDate)));
        }


        return list;
    }

    @GetMapping(value = "/meetings")
    public List<Meeting> getMeetings() {
        List<Meeting> list = meetService.findAll();
        if (list.isEmpty()) {
            poiService.calculate();
        }
        return list;
    }


}
