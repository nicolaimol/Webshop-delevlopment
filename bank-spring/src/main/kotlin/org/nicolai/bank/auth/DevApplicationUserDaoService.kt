package org.nicolai.bank.auth

import org.nicolai.bank.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

@Repository("dev")
class DevApplicationUserDaoService( val passwordEncoder: PasswordEncoder) : ApplicationUserDao {


    override fun selectApplicationUserByUsername(username: String?): Optional<ApplicationUser> {
        for (user in getUsers()) {
            if (user.username == username) {
                return Optional.of(ApplicationUser(username, user.password,
                     HashSet<GrantedAuthority?>(), true,
                    true,true, true))
            }
        }

        return Optional.empty()
    }

    private fun getUsers(): List<User> {

        var users = ArrayList<User>();
        users.add(User("nicolai", passwordEncoder.encode("password")))

        return users
    }
}
