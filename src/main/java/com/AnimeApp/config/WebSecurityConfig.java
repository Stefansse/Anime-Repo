package com.AnimeApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());  // New non-deprecated way of disabling CSRF
        http.authorizeHttpRequests(authz -> authz.anyRequest().permitAll());
        return http.build();
    }
}



//package com.AnimeApp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable());  // Disable CSRF for simplicity
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home", "/login**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login()
//                .loginPage("/login") // You can customize the login page
//                .successHandler((request, response, authentication) -> {
//                    // Handle success logic here, e.g., generate JWT
//                    OAuth2User principal = (OAuth2User) authentication.getPrincipal();
//                    String googleEmail = principal.getAttribute("email");
//                    String jwtToken = jwtService.generateJWTFromGoogle(googleEmail); // Add your custom JWT logic
//                    response.sendRedirect("/home?token=" + jwtToken); // Redirect to your home page with JWT
//                });
//        return http.build();
//    }
//}
