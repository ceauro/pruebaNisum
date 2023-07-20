package com.users.api.services;

import com.users.api.exceptions.DuplicatedDataException;
import com.users.api.models.entities.User;
import com.users.api.models.requests.UserRequest;
import com.users.api.models.responses.ErrorResponse;
import com.users.api.models.responses.GeneralResponse;
import com.users.api.models.responses.UserResponse;
import com.users.api.repositories.UserRepository;
import com.users.api.utils.Ctes;
import com.users.api.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PhoneService phoneService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public GeneralResponse createUser(UserRequest user) throws DuplicatedDataException {
        if(userExists(user.getEmail())){
            throw new DuplicatedDataException(Ctes.Services.EXISTS_EMAIL_ERROR);
        }

        User userEntity = userRepository.save(getUserEntity(user));
        phoneService.savePhones(userEntity, userEntity.getPhones());

        return getUserResponse(userEntity);
    }

    private GeneralResponse getUserResponse(User userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

    private User getUserEntity(UserRequest user){
        User userEntity = modelMapper.map(user, User.class);
        userEntity.setCreatedDate(new Date());
        userEntity.setLastLoginDate(new Date());
        userEntity.setActive(true);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setToken(TokenUtils.createToken(user.getName(), user.getEmail()));
        return userEntity;
    }

    private boolean userExists(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
