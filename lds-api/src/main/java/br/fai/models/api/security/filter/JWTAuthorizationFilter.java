package br.fai.models.api.security.filter;

import br.fai.models.api.security.ApiSecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!checkJWTToken(request)) {
                SecurityContextHolder.clearContext();
                return;
            }

            Jws<Claims> claims = validateToken(request);

            if (claims == null || claims.getBody().get(ApiSecurityConstants.AUTHORITIES) == null) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied... Wasted... We got you");
                return;
            }

            setupSpringAuthentication(claims.getBody());

        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Look likes we got a bug in the matrix");

        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private void setupSpringAuthentication(Claims claims) {

        List<String> authorities = (List<String>) claims.get(ApiSecurityConstants.AUTHORITIES);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }

    private Jws<Claims> validateToken(HttpServletRequest request) {

        String jwtToken = request
                .getHeader(ApiSecurityConstants.HEADER)
                .replace(ApiSecurityConstants.PREFIX, "");

        return Jwts
                .parserBuilder()
                .setSigningKey(ApiSecurityConstants.KEY)
                .build()
                .parseClaimsJws(jwtToken);
    }

    private boolean checkJWTToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(ApiSecurityConstants.HEADER);

        if (authorizationHeader == null || authorizationHeader.startsWith(ApiSecurityConstants.PREFIX)) {
            return false;
        }

        return true;
    }
}
