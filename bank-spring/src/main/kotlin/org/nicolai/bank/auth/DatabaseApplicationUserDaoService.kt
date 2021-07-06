package org.nicolai.bank.auth

import org.springframework.stereotype.Repository
import java.util.*

@Repository("db")
class DatabaseApplicationUserDaoService : ApplicationUserDao {
    //@Autowired
    // User service for getting all users


    override fun selectApplicationUserByUsername(username: String?): Optional<ApplicationUser> {
        /*
        var user: User? = null
        try {
            user = userRepository.findByUsername(username)
        } catch (e: Exception) {
            println("No user found")
        }
        if (user == null) return Optional.empty()
        val applicationUser = ApplicationUser(
            username!!, user.getPassword(),
            null, true,
            true, true, true
        )
        return Optional.of(applicationUser)
        */
         return Optional.empty();
    }
}
