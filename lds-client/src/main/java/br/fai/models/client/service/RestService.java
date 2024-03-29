package br.fai.models.client.service;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface RestService<T> {

    HttpHeaders getAuthenticationHeaders(String username, String password);

    HttpHeaders getRequestHeaders(HttpSession httpSession);

    List<T> get(final String resource, HttpHeaders httpHeaders);

    T getById(final String resouce, Class<T> clazz, HttpHeaders httpHeaders);

    int post(final String resource, T entity, HttpHeaders httpHeaders);

    boolean put(final String resource, final T entity, HttpHeaders httpHeaders);

    boolean deleteById(final String resource, HttpHeaders httpHeaders);

}
