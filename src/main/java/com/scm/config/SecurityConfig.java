package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    private final OAuthSuccessHandler authSuccessHandler;
    private final PasswordEncoderConfig passwordEncoder;

    public SecurityConfig(SecurityCustomUserDetailsService userDetailsService, OAuthSuccessHandler authSuccessHandler, PasswordEncoderConfig passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.authSuccessHandler = authSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }


    // Define AuthenticationManager (connects Spring Security with your custom
    // UserDetailsService)
    @SuppressWarnings("deprecation")
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());

        return daoAuthenticationProvider;
    }

    // Define SecurityFilterChain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/**",                 // ✅ API endpoints
                    "/login", "/signup", "/do-signup",
                    "/oauth2/**", 
                    "/home","/", 
                    "/css/**", "/js/**", "/png/**", "/jpeg/**", "/images/**").permitAll()
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

        http.oauth2Login(oauth2LoginCustomizer -> {
            oauth2LoginCustomizer.loginPage("/login");
            oauth2LoginCustomizer.successHandler(authSuccessHandler);
        });

        http.csrf(AbstractHttpConfigurer::disable);

        http.logout(logoutCustom -> {
            logoutCustom.logoutUrl("/logout");
            logoutCustom.logoutSuccessUrl("/login?logout=true");
        });

        return http.build();
    }
}
