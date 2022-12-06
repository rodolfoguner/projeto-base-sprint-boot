package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserModel> {

    public UserServiceImpl(HttpSession httpSession, RestService<UserModel> restService, RestTemplate restTemplate) {
        this.httpSession = httpSession;
        this.restService = restService;
        this.restTemplate = restTemplate;
    }

    private HttpSession httpSession;

    final String resource = "user/";

    private RestService<UserModel> restService;

    private RestTemplate restTemplate;

    @Override
    public int create(UserModel entity) {

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.post(resource, entity, requestHeaders);
    }

    @Override
    public List<UserModel> find() {

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.get(resource, requestHeaders);
    }

    @Override
    public UserModel findById(int id) {

        if (id <= 0) {
            return null;
        }

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.getById(resource + id, UserModel.class, requestHeaders);
    }

    @Override
    public boolean update(int id, UserModel entity) {

        if (id <= 0) {
            return false;
        }

        if (entity == null) {
            return false;
        }

        if (id != entity.getId()) {
            return false;
        }

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.put(resource + id, entity, requestHeaders);
    }

    @Override
    public boolean deleteById(int id) {

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.deleteById(resource + "/" + id, requestHeaders);
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {

        HttpHeaders httpHeaders = restService.getAuthenticationHeaders(username, password);

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        String resource = "account/login";
        ResponseEntity<UserModel> responseEntity = restTemplate.exchange(
                "http://localhost:8081/api/" + resource,
                HttpMethod.POST,
                httpEntity,
                UserModel.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        UserModel user = responseEntity.getBody();

        return user;

    }
}
