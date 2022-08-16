package br.fai.models.client.service;

import java.util.List;

public interface BaseService<T> {

    int create(T entity);
    List<T> find();
    T findById(int id);
    boolean update(T entity);
    boolean deleteById(int id);

}
