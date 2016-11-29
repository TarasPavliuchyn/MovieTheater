package com.epam.spring.theater.converter;

public interface Converter<S, T> {

    T convertToDto(S model);

    S convertToModel(T dto);
}
