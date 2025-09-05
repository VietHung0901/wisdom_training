package com.Wisdom_Training.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // tắt CSRF
                .authorizeHttpRequests(auth -> auth
                        // Các API không cần xác thực
                        .requestMatchers(HttpMethod.GET, APIURL.URL_ANONYMOUS_GET).permitAll()
                        .requestMatchers(HttpMethod.POST, APIURL.URL_ANONYMOUS_POST).permitAll()

                        // Các API cần quyền ADMIN
                        .requestMatchers(HttpMethod.GET, APIURL.URL_ADMIN_GET).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, APIURL.URL_ADMIN_POST).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, APIURL.URL_ADMIN_PUT).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, APIURL.URL_ADMIN_PATCH).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, APIURL.URL_ADMIN_DELETE).hasAuthority("ADMIN")

                        // các API khác chỉ cần đăng nhập
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT là stateless
                .httpBasic(basic -> {}); // có thể bỏ nếu không dùng Basic Auth

        // Thêm JwtFilter vào trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
