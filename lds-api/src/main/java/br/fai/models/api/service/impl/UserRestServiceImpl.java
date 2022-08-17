package br.fai.models.api.service.impl;

import br.fai.models.api.service.UserRestService;
import br.fai.models.entities.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRestServiceImpl implements UserRestService<UserModel> {

    @Override
    public List<UserModel> find() {
        return null;
    }

    @Override
    public UserModel findById(int id) {
        return null;
    }

    @Override
    public int create(UserModel entity) {
        return 0;
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
    public UserModel validateLogin(String username, String password) {


        return null;
    }
}
