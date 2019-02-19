/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.services;

import com.traceable.poi.domain.Meeting;
import com.traceable.poi.domain.PointInterest;
import com.traceable.poi.domain.Position;
import com.traceable.poi.domain.Vehicle;
import com.traceable.poi.repositories.MeetingRepository;
import com.traceable.poi.repositories.PositionRepository;
import com.traceable.poi.repositories.VehicleRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wesley
 */
@Service
public class PoiService {

    private static final Logger LOG = LoggerFactory.getLogger(PoiService.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private PositionRepository positionRepository;

    private List<PointInterest> interests = new ArrayList<>();

    /**
     *
     * @param vehicle
     * @param interests
     * @return
     */
    public List<Meeting> getMeetingsBy(Vehicle vehicle, List<PointInterest> interests) {
        this.interests = interests;
        //calculate(vehicle);
        // [TO - DO]
        return null;
    }

    public List<Position> getPositionsBy(Vehicle vehicle) {
        List<Position> positions = positionRepository.findAll();
        positions.removeIf((Position pos) -> {
            return !pos.getVehicle().equals(vehicle);
        });

        return positions;
    }

    /**
     *
     * @param vehicle
     * @return
     */
    public List<Meeting> getAllMeetingsBy(Vehicle vehicle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Meeting> calculate(Vehicle vehicle) {
        // pegar a lista de pontos
        //pegar lista de ponto de interesse
        // criar metodo verifica se o ponto esta dentro da area de interesse
        //se retornar true, salvar no banco na tabela meeting o ponto e a area de interesse

        /**
         * [TO - DO]
         */
        return null;
    }

}
