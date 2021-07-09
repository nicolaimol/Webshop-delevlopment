package org.nicolai.bank.auth

import org.nicolai.bank.auth.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.nicolai.bank.auth.ApplicationUserDao
import org.springframework.security.core.userdetails.UserDetailsService
import kotlin.Throws
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class ApplicationUserService @Autowired constructor(@param:Qualifier("dev") private val applicationUserDao: ApplicationUserDao) :
    UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        println(username)
        return applicationUserDao
            .selectApplicationUserByUsername(username)
            .orElseThrow { UsernameNotFoundException(String.format("Username %s not found", username)) }
    }
}
