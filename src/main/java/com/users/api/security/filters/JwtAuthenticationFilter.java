package com.users.api.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.api.models.AuthUser;
import com.users.api.models.requests.LoginRequest;
import com.users.api.models.responses.LoginResponse;
import com.users.api.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException{
        LoginRequest authCredentials;

        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(), LoginRequest.class);
        } catch (IOException e) {
            authCredentials = new LoginRequest();
        }

        return getAuthenticationManager().authenticate(getUpat(authCredentials));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        AuthUser user = (AuthUser) authResult.getPrincipal();
        String token = TokenUtils.createToken(user.getName(), user.getUsername());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().println(new ObjectMapper().writeValueAsString(new LoginResponse(token)));
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }

    private UsernamePasswordAuthenticationToken getUpat(LoginRequest userCredentials){
        return new UsernamePasswordAuthenticationToken(userCredentials.getEmail(),
                userCredentials.getPassword(), Collections.emptyList());
    }
}
