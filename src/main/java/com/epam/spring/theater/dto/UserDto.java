package com.epam.spring.theater.dto;

import com.epam.spring.theater.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String email;
    private String password;
    private String fullName;
    private Date birthDay;
    private UserRole role;
    private Map<Date, Integer> luckyTickets = new HashMap<>();

    private UserDto(UserBuilder builder) {
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

        public UserDto build() {
            return new UserDto(this);
        }
    }

}
