package br.fai.models.api.service.impl;

import br.fai.lds.db.dao.UserDao;
import br.fai.models.api.enums.Credentials;
import br.fai.models.api.service.UserRestService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRestServiceImpl implements UserRestService<UserModel> {

    @Autowired
    private UserDao<UserModel> userDao;

    @Override
    public List<UserModel> find() {
        return userDao.find();
    }

    @Override
    public UserModel findById(int id) {

        if (id <= 0) {
            return null;
        }

        return userDao.findById(id);
    }

    @Override
    public int create(UserModel entity) {

        return 0;
    }

    @Override
    public boolean update(int id, UserModel entity) {

        UserModel user = userDao.findById(id);

        if (user == null) return false;

        user.setEmail(entity.getEmail());
        user.setFullName(entity.getFullName());

        return userDao.update(user);
    }

    @Override
    public boolean deleteById(int id) {
        return userDao.deleteById(id);
    }

    @Override
    public UserModel validateLogin(String encodedData) {

        if (encodedData.isEmpty()) {
            return null;
        }

        Map<Credentials, String> credentialsMap = decodeAndGetUsernameAndPassword(encodedData);

        if (credentialsMap == null || credentialsMap.size() != 2) {
            return null;
        }

        String username = credentialsMap.get(Credentials.USERNAME);
        String password = credentialsMap.get(Credentials.PASSWORD);

        if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        if (username.length() < 4 || password.length() < 3) {
            return null;
        }

        UserModel user = userDao.validateUsernameAndPassword(username, password);

        return user;
    }

    private Map<Credentials, String> decodeAndGetUsernameAndPassword(String encodedData) {
        String[] splitData = encodedData.split("Basic ");

        if (splitData.length != 2) {
            return null;
        }

        String base64Data = splitData[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        String[] firstPart = decodedString.split("Username=");

        if (firstPart.length != 2) {
            return null;
        }

        String[] credentials = firstPart[1].split(";Password=");

        if (credentials.length != 2) {
            return null;
        }

        Map<Credentials, String> credentialsMap = new HashMap<>();

        credentialsMap.put(Credentials.USERNAME, credentials[0]);
        credentialsMap.put(Credentials.PASSWORD, credentials[1]);

        return credentialsMap;
    }
}
