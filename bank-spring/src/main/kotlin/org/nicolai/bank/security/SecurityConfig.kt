package org.nicolai.bank.security

import org.nicolai.bank.auth.ApplicationUserService
import org.nicolai.bank.jwt.JwtConfig
import org.nicolai.bank.jwt.JwtTokenVerifier
import org.nicolai.bank.jwt.JwtUsernameAndPasswordAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.channel.ChannelProcessingFilter
import org.springframework.web.cors.CorsUtils
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.crypto.SecretKey
import javax.servlet.http.HttpSession

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val passwordEncoder: PasswordEncoder,
    private val applicationUserService: ApplicationUserService,
    private val secretKey: SecretKey,
    private val jwtConfig: JwtConfig,
    private val httpSession: HttpSession
    //private val daoAuthenticationProvider: DaoAuthenticationProvider

) : WebSecurityConfigurerAdapter() {


    @Override
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .cors().disable()
            .headers().frameOptions().disable()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
            .addFilterBefore(WebSecurityCorsFilter(), ChannelProcessingFilter::class.java)
            .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey, httpSession))
            .addFilterAfter(JwtTokenVerifier(secretKey, jwtConfig, httpSession), JwtUsernameAndPasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            //.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/h2_console/*").permitAll()
            .antMatchers("/api/test").authenticated()
            .antMatchers("**").permitAll()

    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(applicationUserService)
        return provider
    }

}


