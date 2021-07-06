package org.nicolai.bank.auth

import org.nicolai.bank.auth.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.nicolai.bank.auth.ApplicationUserDao
import org.springframework.security.core.userdetails.UserDetailsService
import kotlin.Throws
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface ApplicationUserDao {
    fun selectApplicationUserByUsername(username: String?): Optional<ApplicationUser>
}
