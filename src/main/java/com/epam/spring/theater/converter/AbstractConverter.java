package com.epam.spring.theater.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<S, T> implements Converter<S, T> {

    public List<T> toDtoList(final Collection<S> sourceList) {
        List<T> resultList = Collections.emptyList();
        if (sourceList != null) {
            resultList = sourceList.stream().map(this::convertToDto).collect(Collectors.toList());
        }
        return resultList;
    }

    public List<S> toModelList(final Collection<T> sourceList) {
        List<S> resultList = Collections.emptyList();
        if (sourceList != null) {
            resultList = sourceList.stream().map(this::convertToModel).collect(Collectors.toList());
        }
        return resultList;
    }

}
