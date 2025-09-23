package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.services.impl.SecurityCustomUserDetailsService;

// @Configuration
// public class SecurityConfig {

//     private final SecurityCustomUserDetailsService userDetailsService;
//     private final SecurityFilterChain securityFilterChain;

//     public SecurityConfig(SecurityCustomUserDetailsService userDetailsService, SecurityFilterChain securityFilterChain) {
//         this.userDetailsService = userDetailsService;
//         this.securityFilterChain = securityFilterChain;
//     }

//     // // user create and login using in memory service
//     // @Bean
//     // public UserDetailsService userDetailsService() {

//     //     UserDetails user1 = User
//     //     .withDefaultPasswordEncoder()
//     //                        .withUsername("admin")
//     //                        .password("admin")
//     //                        .build();

//     //     UserDetails user2 = User
//     //                        .withUsername("admin")
//     //                        .password("admin")
//     //                        .roles("ADMIN", "USER")
//     //                        .build();

//     //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2);

//     //     return inMemoryUserDetailsManager;
//     // }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//         httpSecurity.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
//             authorizeHttpRequestsCustomizer.requestMatchers("/home");
//         });

//         return httpSecurity.build();
//     }

//     @Bean
//     public AuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
//         daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//         daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

//         return daoAuthenticationProvider;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

// }

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityCustomUserDetailsService userDetailsService;

    public SecurityConfig(SecurityCustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Define PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define AuthenticationManager (connects Spring Security with your custom
    // UserDetailsService)
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    // Define SecurityFilterChain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/signup", "/do-signup", "/home","/", "/css/**", "/js/**", "/png/**", "/jpeg/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            );
            // .formLogin(Customizer.withDefaults());

        http.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.defaultSuccessUrl("/user/dashboard");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });

        http.csrf(AbstractHttpConfigurer::disable);

        http.logout(logoutCustom -> {
            logoutCustom.logoutUrl("/logout");
            logoutCustom.logoutSuccessUrl("/login?logout=true");
        });

        return http.build();
    }
}
