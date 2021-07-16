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
import org.springframework.session.Session
import org.springframework.web.filter.OncePerRequestFilter
import java.util.function.Function
import java.util.stream.Collectors
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class JwtTokenVerifier(
    secretKey: SecretKey,
    jwtConfig: JwtConfig,
    httpSession: HttpSession
) : OncePerRequestFilter() {

    private val secretKey: SecretKey
    private val jwtConfig: JwtConfig
    private val httpSession: HttpSession



    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        //println("jwt verify")
        if (request.method == "OPTIONS") {
            return
            //filterChain.doFilter(request, response)
        }


        println(request.method)
        println(SecurityContextHolder.getContext().authentication)
        SecurityContextHolder.getContext().authentication = null

        var authorizationHeader: String? = request.getHeader("Auth")
            authorizationHeader = if (authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")) request.getHeader("Auth") else null
        //println(authorizationHeader)
        var authorization: String? = request.session.getAttribute("Auth") as String?
            authorization = if (authorization != null && authorization.startsWith("Bearer ")) authorization else null


        var cookieToken: String? = null

        if (request.cookies != null) {
            try {
                cookieToken = request.cookies.filter { cookie: Cookie? -> cookie!!.name == "Auth" }[0].value
            } catch (e: Exception) {
                cookieToken = null
            }
        } else {
            cookieToken = null
        }





        /*
        //authorizationHeader = authorization
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader!!.startsWith(jwtConfig.tokenPrefix.toString())) {
            //filterChain.doFilter(request, response)
            //return

        }

         */


        var token: String? = null

        token?.let {
        }
            ?: run { authorizationHeader?.let {
                token = authorizationHeader.replace("Bearer ", "")
                println("authorizationHeader: $authorizationHeader")
            }
        }

        token?.let {
        }
            ?: run {
            cookieToken?.let {
                token = cookieToken
                println("cookieToken")
            }
        }

        token?.let {
        }
            ?: run {
            authorization?.let {
                token = authorization.replace("Bearer ", "")
                println("authorization")
            }
        }

        /*

        if (authorizationHeader != null) {
            println(authorizationHeader == null + " " + authorizationHeader)
            token = authorizationHeader.replace("Bearer ", "")
            //println(token)
            println("authorizationHeader")
        }

        else if (cookieToken != null) {
            token = cookieToken
            println("cookie")
        }

        else if (authorization != null) {
            //token = authorization.replace("Bearer ", "")
            println("authorization")
        }
         */

        //token = null

        if (token == null) {
            println("token null")
            filterChain.doFilter(request, response)
            return
        }

        try {
            println(token)
            val claimsJws: Jws<Claims> = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
            val body: Claims = claimsJws.body
            val username: String = body.subject
            val authorities = body["authorities"] as List<Map<String, String>>
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

            //println(request.session.id)

            SecurityContextHolder.getContext().authentication = authentication
        } catch (e: JwtException) {
            println("not valid")
            //throw IllegalStateException(String.format("Token %s cannot be trusted", token))
        }
        filterChain.doFilter(request, response)
    }

    init {
        this.secretKey = secretKey
        this.jwtConfig = jwtConfig
        this.httpSession = httpSession
    }
}
