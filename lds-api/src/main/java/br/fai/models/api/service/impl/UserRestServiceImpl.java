package br.fai.models.api.service.impl;

import br.fai.lds.db.dao.UserDao;
import br.fai.models.api.service.UserRestService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        decodeAndGetUsernameAndPassword(encodedData);

//        if (username.isEmpty() || password.isEmpty()) {
//            return null;
//        }
//
//        if (username.length() < 4 || password.length() < 3) {
//            return null;
//        }
//
//        return userDao.validateUsernameAndPassword(username, password);

        return null;
    }

    private void decodeAndGetUsernameAndPassword(String encodedData) {

    }
}
