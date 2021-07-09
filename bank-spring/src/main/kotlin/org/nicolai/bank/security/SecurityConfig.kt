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
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.crypto.SecretKey

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val passwordEncoder: PasswordEncoder,
    private val applicationUserService: ApplicationUserService,
    private val secretKey: SecretKey,
    private val jwtConfig: JwtConfig,
    //private val daoAuthenticationProvider: DaoAuthenticationProvider

) : WebSecurityConfigurerAdapter() {


    @Override
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
            .addFilterAfter(JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/h2_console/*").permitAll()
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

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                    .allowCredentials(true)
            }
        }
    }

    /*
    @Bean
    fun cookieProcessorCustomizer(): WebServerFactoryCustomizer<TomcatServletWebServerFactory>? {
        return WebServerFactoryCustomizer { serverFactory: TomcatServletWebServerFactory ->
            serverFactory.addContextCustomizers(
                TomcatContextCustomizer { context: Context ->
                    context.cookieProcessor = LegacyCookieProcessor()
                })
        }
    }

     */

}
