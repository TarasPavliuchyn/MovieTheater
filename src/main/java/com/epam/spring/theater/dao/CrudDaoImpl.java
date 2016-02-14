package com.epam.spring.theater.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class CrudDaoImpl<E, K> implements CrudDao<E, K> {

    private Map<K, E> storage = new HashMap<>();
    private Class<E> type;

    public CrudDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
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
    public Collection<E> findAll() {
        return storage.values();
    }

    public Map<K, E> getStorage() {
        return storage;
    }
}
