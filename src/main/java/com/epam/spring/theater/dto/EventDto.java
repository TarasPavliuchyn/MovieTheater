package com.epam.spring.theater.dto;

import com.epam.spring.theater.model.Rating;
import com.epam.spring.theater.model.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
public class EventDto {
    private String name;
    private BigDecimal basePrice;
    private Rating rating;
}
