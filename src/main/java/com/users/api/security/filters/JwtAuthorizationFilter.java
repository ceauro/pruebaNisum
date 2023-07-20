package com.users.api.security.filters;

import com.users.api.utils.Ctes;
import com.users.api.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(Ctes.Filters.AUTHORIZATION);

        if(Strings.isNotBlank(token) && token.startsWith(Ctes.Filters.BEARER)){
            SecurityContextHolder.getContext()
                    .setAuthentication(TokenUtils.getAuthentication(token.replace(Ctes.Filters.BEARER, Strings.EMPTY)));
        }

        filterChain.doFilter(request, response);
    }
}
