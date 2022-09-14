package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestServiceImpl<T> implements RestService<T> {

    private static final String BASE_ENDPOINT = "http://localhost:8081/api/";

    private String buildEndPoint(String resource) {
        return BASE_ENDPOINT + resource;
    }

    @Override
    public HttpHeaders getAuthenticatedHeaders(String username, String password) {
        return null;
    }

    @Override
    public HttpHeaders getRequestHeaders() {
        return null;
    }

    @Override
    public List<T> get(String resource) {

        List<T> response = null;

        final RestTemplate restTemplate = new RestTemplate();

        try {

            final HttpEntity<String> requestEntity = new HttpEntity<>("");

            ResponseEntity<List<T>> requestResponse = restTemplate.exchange(
                    buildEndPoint(resource),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<T>>() {}
            );
            response = requestResponse.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public T getById(String resouce) {
        return null;
    }

    @Override
    public int post(String resource, T entity) {

        final RestTemplate restTemplate = new RestTemplate();

        try {

            final HttpEntity<T> httpEntity = new HttpEntity<>(entity);

            final ResponseEntity<String> responseEntity = restTemplate.exchange(
                    buildEndPoint(resource),
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            final String response = responseEntity.getBody();

            return Integer.parseInt(resource);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean put(String resource, T entity) {
        return false;
    }

    @Override
    public boolean deleteById(String resorce) {
        return false;
    }
}
