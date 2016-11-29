package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Event {
    private Integer eventId;
    private String name;
    private BigDecimal basePrice;
    private Rating rating;
    private Map<Date, String> schedule = new HashMap<>();
    private List<Ticket> tickets = new ArrayList<>();
}
