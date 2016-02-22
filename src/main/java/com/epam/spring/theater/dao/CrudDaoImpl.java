package com.epam.spring.theater.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class CrudDaoImpl<E, K> implements CrudDao<E, K> {

    private Map<K, E> storage = new HashMap<>();
    private Class<E> type;

    public CrudDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }


    public E createOrUpdate(K id, E model) {
        return storage.put(id, model);
    }

    @Override
    public E find(K id) {
        return storage.get(id);
    }

    @Override
    public void remove(E model) {
        storage.values().remove(model);
    }

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Map<K, E> getStorage() {
        return storage;
    }
}
