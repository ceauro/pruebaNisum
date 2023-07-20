package com.users.api.services;

import com.users.api.models.AuthUser;
import com.users.api.models.entities.User;
import com.users.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(()->new UsernameNotFoundException("El usuario " + email +" no existe."));

        return new AuthUser(user);
    }
}
