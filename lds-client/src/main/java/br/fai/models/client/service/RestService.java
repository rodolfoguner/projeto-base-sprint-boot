package br.fai.models.client.service;

import org.springframework.http.HttpHeaders;

import java.util.List;

public interface RestService<T> {

    HttpHeaders getAuthenticationHeaders(String username, String password);

    HttpHeaders getRequestHeaders();

    List<T> get(final String resource);

    T getById(final String resouce, Class<T> clazz);

    int post(final String resource, T entity);

    boolean put(final String resource, final T entity);

    boolean deleteById(final String resource);

}
