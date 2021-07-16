package org.nicolai.webshop.service;

import org.nicolai.webshop.model.User;
import org.nicolai.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User registerUser (User user) {
        //System.out.println(user);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User newUser =  userRepository.save(user);
            //System.out.println(newUser);
            return newUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(int id) {
        var user = userRepository.findById(id);
        return user.orElse(null);

    }

    public User update(int id, User user) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User delete(int id) {
        var user = userRepository.findById(id);
        userRepository.deleteById(id);
        return user.orElse(null);
    }
}
