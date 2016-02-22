package com.epam.spring.theater.dao;

import java.util.Collection;

public interface CrudDao<E, K> {

    E create(E model);

    E update(E model);

    E find(K id);

    Collection<E> findAll();

    void remove(E model);
}
