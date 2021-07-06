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
import java.lang.RuntimeException
import java.time.LocalDate
import javax.servlet.http.HttpSession

class UsernameAndPasswordAuthenticationRequest {
    var username: String? = null
    var password: String? = null
}
