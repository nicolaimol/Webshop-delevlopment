package org.nicolai.bank.jwt

import com.google.common.base.Strings
import com.google.common.base.Strings.isNullOrEmpty
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.IOException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.function.Function
import java.util.stream.Collectors
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenVerifier(
    secretKey: SecretKey,
    jwtConfig: JwtConfig
) : OncePerRequestFilter() {

    private val secretKey: SecretKey
    private val jwtConfig: JwtConfig
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var authorizationHeader: String? = request.getHeader(jwtConfig.authorizationHeader)
        val authorization = request.getSession().getAttribute("token") as String?
        val cookies = request.cookies

        var cookieAuth: String? = null

        if (cookies != null) {
            for (cookie: Cookie in cookies) {
                //println(cookie.name + " " + cookie.value)
                if (cookie.name == "Auth") {
                    cookieAuth = cookie.value
                }
            }
        }



        authorizationHeader = authorization
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader!!.startsWith(jwtConfig.tokenPrefix.toString())) {
            if (cookieAuth == null) {
                filterChain.doFilter(request, response)
                return
            }
        }

        var token = authorizationHeader?.replace(jwtConfig.tokenPrefix.toString(), "")


        if (cookieAuth != null) {
            token = cookieAuth
        }

        try {
            val claimsJws: Jws<Claims> = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
            val body: Claims = claimsJws.getBody()
            val username: String = body.getSubject()
            val authorities = body.get("authorities") as List<Map<String, String>>
            val simpleGrantedAuthorities: Set<SimpleGrantedAuthority> = authorities.stream()
                .map(Function { m: Map<String, String> ->
                    SimpleGrantedAuthority(
                        m["authority"]
                    )
                })
                .collect(Collectors.toSet())
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
            )
            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: JwtException) {
            throw IllegalStateException(String.format("Token %s cannot be trusted", token))
        }
        filterChain.doFilter(request, response)
    }

    init {
        this.secretKey = secretKey
        this.jwtConfig = jwtConfig
    }
}
