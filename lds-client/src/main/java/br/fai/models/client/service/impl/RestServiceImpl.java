package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import br.fai.models.entities.UserModel;
import com.google.gson.Gson;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class RestServiceImpl<T> implements RestService<T> {

    private static final String BASE_ENDPOINT = "http://localhost:8081/api/";

    private String buildEndPoint(String resource) {
        return BASE_ENDPOINT + resource;
    }

    @Override
    public HttpHeaders getAuthenticationHeaders(String username, String password) {
        String auth = "Username=" + username + ";Password=" + password;

        byte[] encodedBytes;

        try {
            encodedBytes = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

            String headers = "Basic " + new String(encodedBytes);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", headers);

            return httpHeaders;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpHeaders getRequestHeaders(HttpSession httpSession) {
        try {
            UserModel user = (UserModel) httpSession.getAttribute("currentUser");

            String authHeader = "Bearer " + user.getToken();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", authHeader);

            return httpHeaders;

        } catch (Exception e) {
            e.printStackTrace();
            return new HttpHeaders();
        }
    }

    @Override
    public List<T> get(String resource, HttpHeaders httpHeaders) {

        List<T> response = null;

        final RestTemplate restTemplate = new RestTemplate();

        try {

            final HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity<List<T>> requestResponse = restTemplate.exchange(
                    buildEndPoint(resource),
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<T>>() {
                    }
            );
            response = requestResponse.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public T getById(String resouce, Class<T> clazz, HttpHeaders httpHeaders) {

        T response = null;

        final RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<String> resquestResponse;
        try {
            HttpEntity<String> requestEntity;

            if (httpHeaders != null) {
                requestEntity = new HttpEntity<>(httpHeaders);
            } else {
                requestEntity = new HttpEntity<>("");
            }

            resquestResponse = restTemplate.exchange(
                    buildEndPoint(resouce),
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            final Gson gson = new Gson();

            response = gson.fromJson(resquestResponse.getBody(), clazz);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public int post(String resource, T entity, HttpHeaders httpHeaders) {

        final RestTemplate restTemplate = new RestTemplate();

        try {

            final HttpEntity<T> httpEntity = new HttpEntity<>(entity, httpHeaders);

            final ResponseEntity<String> responseEntity = restTemplate.exchange(
                    buildEndPoint(resource),
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            final String response = responseEntity.getBody();

            return Integer.parseInt(response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean put(String resource, T entity, HttpHeaders httpHeaders) {

        boolean response = false;

        final RestTemplate restTemplate = new RestTemplate();


        try {
            final HttpEntity<T> httpEntity = new HttpEntity<>(entity, httpHeaders);

            final ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                    buildEndPoint(resource),
                    HttpMethod.PUT,
                    httpEntity,
                    Boolean.class
            );

            response = responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public boolean deleteById(String resource, HttpHeaders httpHeaders) {

        boolean response = false;

        final RestTemplate restTemplate = new RestTemplate();


        try {

            final HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

            final ResponseEntity<Boolean> requestResponse = restTemplate.exchange(
                    buildEndPoint(resource),
                    HttpMethod.DELETE,
                    httpEntity,
                    Boolean.class
            );

            response = requestResponse.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
