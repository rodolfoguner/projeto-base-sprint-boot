package br.fai.models.api.service.impl;

import br.fai.models.api.security.ApiSecurityConstants;
import br.fai.models.api.service.JWTService;
import br.fai.models.entities.UserModel;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JWTServiceImpl implements JWTService {

    @Override
    public String getJWTToken(UserModel userModel) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + userModel.getType()));

        try {
            String token = Jwts
                    .builder()
                    .setId("FAI_LDS_2022")
                    .setSubject(userModel.getUsername())
                    .claim(
                            ApiSecurityConstants.AUTHORITIES,
                            grantedAuthorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList())
                    )
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + convertToMinutes(60)))
                    .signWith(ApiSecurityConstants.KEY)
                    .compact();

            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiSecurityConstants.INVALID_TOKEN;
        }
    }

    private static int convertToMinutes(int minutes) {
        return minutes * 60 * 1000;
    }
}
