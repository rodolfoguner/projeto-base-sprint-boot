package br.fai.models.client.service.impl;

import br.fai.models.client.service.RestService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class RestServiceImpl implements RestService {
    @Override
    public HttpHeaders getAuthenticatedHeaders(String username, String password) {
        return null;
    }

    @Override
    public HttpHeaders getRequestHeaders() {
        return null;
    }
}
