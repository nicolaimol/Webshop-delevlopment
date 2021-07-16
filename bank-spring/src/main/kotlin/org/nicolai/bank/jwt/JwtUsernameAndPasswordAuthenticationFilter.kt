package org.nicolai.bank.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.sql.Date
import java.time.LocalDate
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession


class JwtUsernameAndPasswordAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    jwtConfig: JwtConfig,
    secretKey: SecretKey,
    httpSession: HttpSession
) : UsernamePasswordAuthenticationFilter() {
    private val jwtConfig: JwtConfig
    private val secretKey: SecretKey
    private val httpSession: HttpSession



    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        return try {
            val authenticationRequest: UsernameAndPasswordAuthenticationRequest = ObjectMapper()
                .readValue<UsernameAndPasswordAuthenticationRequest>(
                    request.inputStream,
                    UsernameAndPasswordAuthenticationRequest::class.java
                )
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                authenticationRequest.username,
                authenticationRequest.password
            )
            authenticationManager.authenticate(authentication)


        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    protected override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {

        val token: String = Jwts.builder()
            .setSubject(authResult.name)
            .claim("authorities", authResult.authorities)
            .setIssuedAt(java.util.Date())
            .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.tokenExpirationAfterDays?.toLong()!!)))
            .signWith(secretKey)
            .compact()

        val session: HttpSession = request.session
        session.setAttribute("Auth", jwtConfig.tokenPrefix + token)

        val cookie = Cookie("Auth", token)
        cookie.isHttpOnly = true
        cookie.maxAge = 7 * 24 * 60
        response.addCookie(cookie)

        response.addHeader(jwtConfig.authorizationHeader, jwtConfig.tokenPrefix + token)
        response.writer.println("{\"Auth\": \"Bearer $token\"}")

    }

    init {
        this.authenticationManager = authenticationManager
        this.jwtConfig = jwtConfig
        this.secretKey = secretKey
        this.httpSession = httpSession
    }

}
