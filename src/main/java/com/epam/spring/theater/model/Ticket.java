package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Ticket  implements Serializable{
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer ticketId;
    private boolean purchased;
    private boolean booked;
    private boolean discounted;
    private Event event;
    private Date dateTime;
    private BigDecimal ticketPrice;

    public Ticket() {
        this.ticketId = count.incrementAndGet();
    }
}
