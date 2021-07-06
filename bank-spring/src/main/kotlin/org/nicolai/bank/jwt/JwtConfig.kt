package org.nicolai.bank.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.beans.factory.annotation.Autowired
import org.nicolai.bank.jwt.JwtConfig
import javax.crypto.SecretKey
import kotlin.Throws
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.FilterChain
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.lang.IllegalStateException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.nicolai.bank.jwt.UsernameAndPasswordAuthenticationRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import java.lang.RuntimeException
import java.time.LocalDate
import javax.servlet.http.HttpSession

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
class JwtConfig {
    var secretKey: String? = null
    var tokenPrefix: String? = null
    var tokenExpirationAfterDays: Int? = null
    val authorizationHeader: String
        get() = HttpHeaders.AUTHORIZATION
}
