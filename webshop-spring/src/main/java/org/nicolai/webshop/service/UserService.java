package org.nicolai.webshop.service;

import org.nicolai.webshop.model.User;
import org.nicolai.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean registerUser (User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser =  userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }


    }
}
