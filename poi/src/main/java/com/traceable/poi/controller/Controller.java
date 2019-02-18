/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.controller;

import com.traceable.poi.domain.Vehicle;
import com.traceable.poi.repositories.VehicleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private VehicleRepository vehicleRepository;

    @GetMapping(value = "/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
