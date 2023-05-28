package com.williamhdz.quickpoll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/v1/**", "/v2/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                                .requestMatchers("/v3/polls/**").authenticated()
                )
                .formLogin((form) -> form
                                .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        @SuppressWarnings("deprecation")
		UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("ADMIN")
                        .build();
        
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());

        return new InMemoryUserDetailsManager(user);
    }
	
}
