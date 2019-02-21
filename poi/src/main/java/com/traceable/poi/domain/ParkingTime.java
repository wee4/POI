/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Wesley
 * Classe criada para salvar relação e duração que um carro permanece dentro de um ponto
 */
@Entity
@Table(name = "parking")
@SequenceGenerator(name = "parking_id_seq", initialValue = 1, allocationSize = 1)
public class ParkingTime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_id_seq")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "point_interest_id")
    private PointInterest interest;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    
    @Column(name = "time_parking")
    private Long timeParking;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PointInterest getInterest() {
        return interest;
    }

    public void setInterest(PointInterest interest) {
        this.interest = interest;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getTimeParking() {
        return timeParking;
    }

    public void setTimeParking(Long timeParking) {
        this.timeParking = timeParking;
    }

}
