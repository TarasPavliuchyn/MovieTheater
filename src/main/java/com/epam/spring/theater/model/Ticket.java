package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Ticket implements Serializable {
    private Integer ticketId;
    private boolean purchased;
    private boolean booked;
    private boolean discounted;
    private Integer eventId;
    private Date dateTime;
    private BigDecimal ticketPrice;
    private Integer userId;
    private Integer seat;
}
