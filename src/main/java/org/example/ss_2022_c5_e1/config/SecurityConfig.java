package org.example.ss_2022_c5_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                .authorizeRequests()
//                .anyRequest().authenticated() // endpoint level authorization
                // the request can be denied completely even before it reaches the controller
//                .anyRequest().permitAll()
//                .anyRequest().denyAll()
//                .anyRequest().hasAuthority("read")
//                .anyRequest().hasAnyAuthority("read", "write")
//                .anyRequest().hasRole("ADMIN")
//                .anyRequest().hasAnyRole("ADMIN", "MANAGER")
                .anyRequest().access("")
                .and().build();

        // .anyRequest().authenticated() === matcher method + authorization rule
        // 1. which matter methods should you use and how
        // 2. how to apply different authorization rules
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var uds = new InMemoryUserDetailsManager();

        var u1 = User.withUsername("bill")
                .password(passwordEncoder().encode("12345"))
//                .authorities("ROLE_ADMIN")
                .authorities("read") // we can add "ROLE_ADMIN" to this and keep both
                .build();

        var u2 = User.withUsername("john")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        var u3 = User.withUsername("jake")
                .password(passwordEncoder().encode("12345"))
                .roles("MANAGER") // equivalent with an authority named ROLE_MANAGER
                .build();


        uds.createUser(u1);
        uds.createUser(u2);
        uds.createUser(u3);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}