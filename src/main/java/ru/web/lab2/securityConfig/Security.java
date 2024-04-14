/*package ru.web.lab2.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class Security{
    @Value("${security.logins}")
    private String[] logins;

    @Value("${security.passwords}")
    private String[] passwords;

    @Value("${security.roles}")
    private String[] roles;
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails[] userDetailsArray = new UserDetails[logins.length];
        for (int i = 0; i < logins.length; i++) {
            userDetailsArray[i] = User.withUsername(logins[i])
                    .password(passwordEncoder().encode(passwords[i]))
                    .roles(roles[i])
                    .build();
        }
        return new InMemoryUserDetailsManager(userDetailsArray[0], userDetailsArray[1]);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    public class WebSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/projects.html", true)
                    .permitAll()
                )
                .logout(logout -> logout
                    .permitAll()
                );
                return http.build();
        }
    }
}*/