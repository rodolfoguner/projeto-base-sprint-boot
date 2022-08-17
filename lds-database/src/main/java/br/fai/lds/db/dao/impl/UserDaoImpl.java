package br.fai.lds.db.dao.impl;

import br.fai.lds.db.dao.UserDao;
import br.fai.models.entities.UserModel;

import java.util.List;

public class UserDaoImpl implements UserDao<UserModel> {
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
    public UserModel validateUsernameAndPassword(String username, String password) {
        return null;
    }
}
