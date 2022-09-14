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

    @Autowired
    private RestService restService;

    @Override
    public int create(UserModel entity) {
        return 0;
    }

    @Override
    public List<UserModel> find() {
        return null;
    }

    @Override
    public UserModel findById(int id) {
        return null;
    }

    @Override
    public boolean update(UserModel entity) {
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public UserModel validateUsernameAndPassword(String username, String password) {

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<String> httpEntity = new HttpEntity<>("");

            String resource = "account/login?username=" + username +
                    "&password=" + password;
            ResponseEntity<UserModel> responseEntity = restTemplate.exchange(buildEndPoint(resource),
                    HttpMethod.POST, httpEntity, UserModel.class);

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                return null;
            }

            UserModel user = responseEntity.getBody();

            return user;
        } catch (RestClientException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
