package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserModel> {

    final String resource = "user/";

    @Autowired
    private RestService<UserModel> restService;

    @Override
    public int create(UserModel entity) {
        return restService.post(resource, entity);
    }

    @Override
    public List<UserModel> find() {
        return restService.get(resource);
    }

    @Override
    public UserModel findById(int id) {
        return restService.getById(resource + "/" + id, UserModel.class);
    }

    @Override
    public boolean update(int id, UserModel entity) {
        return restService.put(resource + "/" + id, entity);
    }

    @Override
    public boolean deleteById(int id) {
        return restService.deleteById(resource + "/" + id);
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<String> httpEntity = new HttpEntity<>("");

            String resource = "account/login?username=" + username +
                    "&password=" + password;
            ResponseEntity<UserModel> responseEntity = restTemplate.exchange(
                    "http://localhost:8081/api/account/login",
                    HttpMethod.POST,
                    httpEntity,
                    UserModel.class);

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                return null;
            }

            UserModel user = responseEntity.getBody();

            return user;
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
