package com.epam.spring.theater.service;

import com.epam.spring.theater.model.Auditorium;

import java.util.List;

public interface AuditoriumService {

    List<Auditorium> getAuditoriums();

    Integer getSeatsNumber(String auditoriumName);

    List<Integer> getVipSeats(String auditoriumName);
}
