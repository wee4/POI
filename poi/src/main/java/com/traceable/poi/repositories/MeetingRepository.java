/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.traceable.poi.repositories;

import com.traceable.poi.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Wesley Fermino <wesleycz@live.com>
 * Feb 17 2019
 */
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    
}
