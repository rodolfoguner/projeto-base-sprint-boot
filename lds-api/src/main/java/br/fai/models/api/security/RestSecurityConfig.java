package br.fai.models.api.security;

import br.fai.models.api.security.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .addFilterAfter(
                        new JWTAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/account/login")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
