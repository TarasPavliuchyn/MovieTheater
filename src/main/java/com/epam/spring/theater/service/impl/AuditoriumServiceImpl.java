package com.epam.spring.theater.service.impl;

import com.epam.spring.theater.model.Auditorium;
import com.epam.spring.theater.service.AuditoriumService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    private Map<String, Auditorium> auditoriums;

    @Override
    public List<Auditorium> getAuditoriums() {
        return new ArrayList<>(auditoriums.values());
    }

    @Override
    public Integer getSeatsNumber(String auditoriumName) {
        return auditoriums.get(auditoriumName).getSeatNumber();
    }

    @Override
    public List<Integer> getVipSeats(String auditoriumName) {
        return auditoriums.get(auditoriumName).getVipSeats();
    }

    @Override
    public Auditorium findByName(String auditoriumName) {
        return auditoriums.get(auditoriumName);
    }
}
