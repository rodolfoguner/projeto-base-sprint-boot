package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
