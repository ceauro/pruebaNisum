package com.users.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Ctes {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Validators{
        public static final String NO_EXISTS_PARAMETRIZATION_ERROR = "Configuración no parametrizada";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Services{
        public static final String EXISTS_EMAIL_ERROR = "El correo ya existe";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Filters{
        public static final String AUTHORIZATION = "Authorization";
        public static final String BEARER = "Bearer ";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Security{
        public static final String[] AUTH_WHITELIST = {
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        };
    }
}
