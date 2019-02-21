/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.services;

import com.traceable.poi.domain.Meeting;
import com.traceable.poi.domain.ParkingTime;
import com.traceable.poi.domain.PointInterest;
import com.traceable.poi.domain.Position;
import com.traceable.poi.domain.Vehicle;
import com.traceable.poi.repositories.MeetingRepository;
import com.traceable.poi.repositories.ParkingTimeRepository;
import com.traceable.poi.repositories.PointInterestRepository;
import com.traceable.poi.repositories.PositionRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import org.apache.commons.collections4.CollectionUtils;
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
    private MeetingRepository meetingRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PointInterestRepository interestRepository;

    @Autowired
    private ParkingTimeRepository parkingTimeRepository;
    
    
    /**
     * 
     * @return 
     */
    public List<Meeting> calculate() {
        List<Position> positions = positionRepository.findAll();
        List<PointInterest> interests = interestRepository.findAll();
        List<Meeting> generatedMeetings = generateMeetings(positions, interests);
        
        generateParkings(generatedMeetings, interests);

        return generatedMeetings;
    }

    /**
     *
     * @param positions
     * @param interests
     * @return
     */
    private List<Meeting> generateMeetings(List<Position> positions, List<PointInterest> interests) {
        List<Meeting> lst = new ArrayList<>();

        for (PointInterest inter : interests) {
            for (Position position : positions) {
                if (verifyMeeting(position, inter)) {
                    Meeting m = new Meeting();
                    m.setInterest(inter);
                    m.setPosition(position);
                    try {
                        lst.add(meetingRepository.save(m));
                    } catch (Exception e) {
                        LOG.error("Exception on save: {} ", e);
                        return new ArrayList<>();
                    }

                }
            }
        }
        return lst;
    }

    /**
     *
     * @param pos
     * @param interests
     * @return
     */
    private boolean verifyMeeting(Position pos, PointInterest interests) {

        //double earthRadius = 3958.75;//miles
        double dLat = Math.toRadians(Math.abs(interests.getLatitude()) - Math.abs(pos.getLatitude()));
        double dLng = Math.toRadians(Math.abs(interests.getLongitude()) - Math.abs(pos.getLongitude()));
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(Math.abs(pos.getLatitude())))
                * Math.cos(Math.toRadians(interests.getLatitude()));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        Boolean b = c < interests.getRadius();
        return b;
    }

    /**
     *
     * @param generatedMeetings
     * @param interests
     * Método que calcula em minutos, o quanto cada carro ficoudentro de um ponto
     */
    private void generateParkings(List<Meeting> generatedMeetings, List<PointInterest> interests) {
        List<ParkingTime> parkingTimes = new ArrayList<>();

        interests.forEach((PointInterest pi) -> {

            generatedMeetings.stream().filter((Meeting t) -> t.getInterest().equals(pi));

            Collection<Vehicle> vehicles = CollectionUtils.collect(generatedMeetings, (Meeting meeting) -> meeting.getPosition().getVehicle());
            Set<Vehicle> set = new HashSet<>(vehicles);

            for (Vehicle vehicle : set) {

                generatedMeetings.stream().filter((Meeting t) -> t.getPosition().getVehicle().equals(vehicle));

                generatedMeetings.sort((Meeting o1, Meeting o2) -> o1.getPosition().getSentDate().compareTo(o2.getPosition().getSentDate()));

                Date firstDateOnThisPoint = generatedMeetings.stream().findFirst().get().getPosition().getSentDate();
                Date lastDateOnThisPoint = generatedMeetings.get(generatedMeetings.size() - 1).getPosition().getSentDate();

                long parkingTime = getDateDiff(firstDateOnThisPoint, lastDateOnThisPoint, TimeUnit.MINUTES);

                ParkingTime p = new ParkingTime();
                p.setInterest(pi);
                p.setVehicle(vehicle);
                p.setTimeParking(parkingTime);

                parkingTimes.add(p);
            }
        });

        try {
            parkingTimeRepository.saveAll(parkingTimes);
        } catch (Exception e) {
            LOG.error("Exception on save: {}", e);
        }
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}
