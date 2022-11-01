package br.fai.models.api.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class ApiSecurityConstants {

    public static final String HEADER = "Authorization";

    public static final String PREFIX = "Bearer ";

    public static final SecretKey KEY = Keys.hmacShaKeyFor("DSLKDJSAKDJKLASJDLKSJAKDjsakdjskdjklasjdklasjdklasjdklasjdklasjd".getBytes(StandardCharsets.UTF_8));
    public static final String AUTHORITIES = "authorities";
}
