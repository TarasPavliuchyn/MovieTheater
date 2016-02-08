package com.epam.spring.theater.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class User {
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer userId;
    private String email;
    private String password;
    private String fullName;
    private Date birthDay;
    private UserRole role;
    private List<Ticket> tickets = new ArrayList<>();

    private User(UserBuilder builder) {
        this.userId = count.incrementAndGet();
        this.email = builder.email;
        this.password = builder.password;
        this.fullName = builder.fullName;
        this.birthDay = builder.birthDay;
        this.role = builder.role;
    }

    public static class UserBuilder {
        private String email;
        private String password;
        private String fullName;
        private Date birthDay;
        private UserRole role;

        public UserBuilder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public UserBuilder() {
        }

        public UserBuilder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public UserBuilder birthDay(Date birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public UserBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
