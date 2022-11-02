package br.fai.models.api.service;

import br.fai.models.entities.UserModel;

public interface JWTService {

    String getJWTToken(UserModel userModel);

}
