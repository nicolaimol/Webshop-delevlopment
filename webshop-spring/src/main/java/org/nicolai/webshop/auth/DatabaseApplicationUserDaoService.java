package org.nicolai.webshop.auth;

import org.nicolai.webshop.model.Admin;
import org.nicolai.webshop.model.User;
import org.nicolai.webshop.repository.AdminRepository;
import org.nicolai.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("db")
public class DatabaseApplicationUserDaoService implements ApplicationUserDao{

    @Autowired
    AdminRepository adminRepository;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {

        Admin user = null;
        try {
            user = adminRepository.findByUsername(username);
            System.out.println(user.getRole().getGrantedAuthorities());
        } catch (Exception e) {
            System.out.println("No user found");
        }
        if (user == null) return Optional.empty();
        ApplicationUser applicationUser = new ApplicationUser(username, user.getPassword(),
                user.getRole().getGrantedAuthorities(), true,
                true ,true, true);
        return Optional.of(applicationUser);
    }
}
