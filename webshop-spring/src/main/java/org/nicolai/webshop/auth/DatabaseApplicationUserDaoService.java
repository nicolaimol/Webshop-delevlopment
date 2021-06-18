package org.nicolai.webshop.auth;

import org.nicolai.webshop.model.User;
import org.nicolai.webshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("db")
public class DatabaseApplicationUserDaoService implements ApplicationUserDao{

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {

        User user = null;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            System.out.println("No user found");
        }
        if (user == null) return Optional.empty();
        ApplicationUser applicationUser = new ApplicationUser(username, user.getPassword(),
                null, true,
                true ,true, true);
        return Optional.of(applicationUser);
    }
}
