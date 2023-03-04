package uy.amn.dummygen

import org.springframework.context.annotation.Bean
import org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {

        http.authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .requestMatchers("/data-generator", "/submit-dummy-table").access(hasRole("USER"))
            .requestMatchers("/api/**").hasRole("ADMIN")
            .and()
            .formLogin()
        return http.build()
    }

}