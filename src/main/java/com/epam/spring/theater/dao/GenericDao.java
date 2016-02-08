package com.epam.spring.theater.dao;

import java.util.Collection;

public interface GenericDao<E, K> {

    E create(K key, E model);

    E find(K id);

    Collection<E> findAll();

    void remove(E model);
}
