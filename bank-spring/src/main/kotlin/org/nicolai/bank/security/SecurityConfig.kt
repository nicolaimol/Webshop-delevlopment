package org.nicolai.bank.security

import org.nicolai.bank.auth.ApplicationUserService
import org.nicolai.bank.jwt.JwtConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import javax.crypto.SecretKey

class SecurityConfig : WebSecurityConfigurerAdapter() {

    private var passwordEncoder: PasswordEncoder? = null
    private var applicationUserService: ApplicationUserService? = null
    private var secretKey: SecretKey? = null
    private var jwtConfig: JwtConfig? = null
    private var daoAuthenticationProvider: DaoAuthenticationProvider? = null

    @Autowired
    fun ApplicationSecurityConfig(
        passwordEncoder: PasswordEncoder,
        applicationUserService: ApplicationUserService,
        secretKey: SecretKey,
        jwtConfig: JwtConfig,
        daoAuthenticationProvider: DaoAuthenticationProvider
    ) {
        this.passwordEncoder = passwordEncoder
        this.applicationUserService = applicationUserService
        this.secretKey = secretKey
        this.jwtConfig = jwtConfig
        this.daoAuthenticationProvider = daoAuthenticationProvider
    }

    @Override
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("**").permitAll();


        /*
        .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("**").permitAll();

         */

    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider)
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(applicationUserService)
        return provider
    }

}
