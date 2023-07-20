package com.users.api.services;

import com.users.api.models.entities.Phone;
import com.users.api.models.entities.User;
import com.users.api.repositories.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public void savePhones(User user, List<Phone> phones){
        if(Objects.nonNull(user) && Objects.nonNull(phones) && !phones.isEmpty()) {
            phones.forEach(phone -> {
                phone.setUser(user);
                phoneRepository.save(phone);
            });
        }
    }
}
