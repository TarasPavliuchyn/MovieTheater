package com.epam.spring.theater.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TicketDto {
    private Integer ticketId;
    private String eventName;
    private Integer userId;
    private boolean purchased;
    private boolean booked;
    private boolean discounted;
    private Date dateTime;
    private BigDecimal ticketPrice;
    private Integer seat;
}
