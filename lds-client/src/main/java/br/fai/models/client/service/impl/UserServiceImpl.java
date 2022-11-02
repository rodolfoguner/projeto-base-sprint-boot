package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserModel> {

    public UserServiceImpl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    private HttpSession httpSession;

    final String resource = "user/";

    @Autowired
    private RestService<UserModel> restService;

    @Override
    public int create(UserModel entity) {
        return restService.post(resource, entity);
    }

    @Override
    public List<UserModel> find() {

        HttpHeaders requestHeaders = restService.getRequestHeaders(httpSession);

        return restService.get(resource, requestHeaders);
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
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
