package org.nicolai.webshop.service;

import org.nicolai.webshop.model.Admin;
import org.nicolai.webshop.model.User;
import org.nicolai.webshop.repository.AdminRepository;
import org.nicolai.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Admin registerUser (Admin user) {
        //System.out.println(user);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Admin newUser =  adminRepository.save(user);
            //System.out.println(newUser);
            return newUser;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    public Admin getById(String username) {
        var user = adminRepository.findById(username);
        return user.orElse(null);

    }

    public Admin update(String username, Admin user) {
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return adminRepository.save(user);
    }

    public Admin delete(String id) {
        var user = adminRepository.findById(id);
        adminRepository.deleteById(id);
        return user.orElse(null);
    }
}
