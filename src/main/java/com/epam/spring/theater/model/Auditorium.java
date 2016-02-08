package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Auditorium {
    private String name;
    private Integer seatNumber;
    private List<Integer> vipSeats;
}
