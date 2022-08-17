package br.fai.models.api.service;

import br.fai.models.entities.UserModel;

public interface UserRestService<T> extends BaseRestService<T> {

    UserModel validateLogin(String username, String password);
}
