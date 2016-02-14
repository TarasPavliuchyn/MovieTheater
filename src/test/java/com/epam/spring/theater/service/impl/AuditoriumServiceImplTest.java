package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.AbstractTestSuite;
import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.service.AuditoriumService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuditoriumServiceImplTest extends AbstractTestSuite {

    @Value("${auditorium.red.seats}")
    private Integer auditoriumSeat;

    @Value("${auditorium.red.name}")
    private String auditoriumName;

    @Value("#{'${auditorium.red.vip.seats}'.split(',')}")
    private List<Integer> vipSeats;

    @Autowired
    private AuditoriumService auditoriumService;


    @Test
    public void testGetAuditoriums() {
        List<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        assertTrue(!auditoriums.isEmpty());
    }

    @Test
    public void testGetSeatsNumber() {
        Integer actualSeatsNumber = auditoriumService.getSeatsNumber(auditoriumName);
        assertEquals(auditoriumSeat, actualSeatsNumber);
    }

    @Test
    public void testGetVipSeats() {
        List<Integer> actualVipSeats = auditoriumService.getVipSeats(auditoriumName);
        assertEquals(vipSeats, actualVipSeats);
    }
}