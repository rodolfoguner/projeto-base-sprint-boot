package br.fai.models.client.service;

import br.fai.models.entities.UserModel;

public interface UserService<T> extends BaseService<T> {

    UserModel validateUsernameAndPassword(String username, String password);

}
