package br.fai.models.client.config.security.providers;


import br.fai.models.client.service.UserService;
import br.fai.models.entities.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FaiAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService<UserModel> userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserModel userModel = userService.validateUsernameAndPassword(username, password);

        if (userModel == null) {
            return null;
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        return new UsernamePasswordAuthenticationToken(new UserModel(), password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
